package com.wego.seolstudybe.chat.service;

import com.wego.seolstudybe.chat.dto.ChatMessageRequestDTO;
import com.wego.seolstudybe.chat.dto.ChatMessageResponseDTO;
import com.wego.seolstudybe.chat.entity.ChatMessage;
import com.wego.seolstudybe.chat.entity.ChatRoom;
import com.wego.seolstudybe.chat.entity.enums.MessageType;
import com.wego.seolstudybe.chat.repository.ChatMessageRepository;
import com.wego.seolstudybe.chat.repository.ChatRoomRepository;
import com.wego.seolstudybe.common.error.ErrorCode;
import com.wego.seolstudybe.chat.exception.ChatRoomNotFoundException;
import com.wego.seolstudybe.chat.exception.InvalidChatException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;

    /**
     * 메시지 저장
     */
    public ChatMessageResponseDTO saveMessage(ChatMessageRequestDTO request) {
        // 채팅방 존재 확인
        ChatRoom chatRoom = chatRoomRepository.findById(request.getRoomId())
                .orElseThrow(ChatRoomNotFoundException::new);

        // 메시지 유효성 검사
        validateMessage(request);

        // 메시지 저장
        ChatMessage message = ChatMessage.builder()
                .roomId(request.getRoomId())
                .senderId(request.getSenderId())
                .messageType(request.getMessageType())
                .content(request.getContent())
                .fileUrl(request.getFileUrl())
                .fileName(request.getFileName())
                .build();

        ChatMessage savedMessage = chatMessageRepository.save(message);

        // 채팅방 마지막 메시지 업데이트
        String lastMessage = resolveLastMessage(request);
        chatRoom.updateLastMessage(lastMessage, request.getSenderId());
        chatRoom.incrementUnreadCount(request.getSenderId());
        chatRoomRepository.save(chatRoom);

        log.debug("메시지 저장 완료: messageId={}, roomId={}", savedMessage.getId(), request.getRoomId());
        return ChatMessageResponseDTO.from(savedMessage);
    }

    /**
     * 메시지 유효성 검사
     */
    private void validateMessage(ChatMessageRequestDTO request) {
        // TEXT 타입인데 내용이 없는 경우
        if (request.getMessageType() == MessageType.TEXT &&
                (request.getContent() == null || request.getContent().isBlank())) {
            throw new InvalidChatException(ErrorCode.BAD_REQUEST, "메시지 내용을 입력해주세요.");
        }

        // 파일 타입인데 URL이 없는 경우
        if ((request.getMessageType() == MessageType.IMAGE ||
                request.getMessageType() == MessageType.VIDEO ||
                request.getMessageType() == MessageType.FILE) &&
                (request.getFileUrl() == null || request.getFileUrl().isBlank())) {
            throw new InvalidChatException(ErrorCode.BAD_REQUEST, "파일 URL이 필요합니다.");
        }
    }

    /**
     * 채팅방 메시지 이력 조회 (페이징)
     */
    public List<ChatMessageResponseDTO> getMessages(String roomId, int page, int size) {
        validateRoomExists(roomId);

        Pageable pageable = PageRequest.of(page, size);
        Page<ChatMessage> messages = chatMessageRepository.findByRoomIdOrderBySentAtDesc(roomId, pageable);

        return messages.getContent().stream()
                .map(ChatMessageResponseDTO::from)
                .toList();
    }

    /**
     * 메시지 읽음 처리
     */
    public void markMessagesAsRead(String roomId, Long readerId) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(ChatRoomNotFoundException::new);

        List<ChatMessage> unreadMessages = chatMessageRepository
                .findByRoomIdAndIsReadFalseAndSenderIdNot(roomId, readerId);

        if (!unreadMessages.isEmpty()) {
            unreadMessages.forEach(ChatMessage::markAsRead);
            chatMessageRepository.saveAll(unreadMessages);

            // 채팅방 읽지 않은 메시지 수 초기화
            chatRoom.resetUnreadCount(readerId);
            chatRoomRepository.save(chatRoom);

            log.debug("메시지 읽음 처리: roomId={}, readerId={}, count={}", roomId, readerId, unreadMessages.size());
        }
    }

    /**
     * 읽지 않은 메시지 수 조회
     */
    public long getUnreadCount(String roomId, Long userId) {
        validateRoomExists(roomId);
        return chatMessageRepository.countByRoomIdAndIsReadFalseAndSenderIdNot(roomId, userId);
    }

    /**
     * 채팅방의 모든 미디어/파일 조회 (이미지, 동영상, 파일)
     */
    public List<ChatMessageResponseDTO> getMediaFiles(String roomId) {
        validateRoomExists(roomId);

        List<MessageType> mediaTypes = List.of(MessageType.IMAGE, MessageType.VIDEO, MessageType.FILE);
        List<ChatMessage> mediaMessages = chatMessageRepository
                .findByRoomIdAndMessageTypeInOrderBySentAtDesc(roomId, mediaTypes);

        return mediaMessages.stream()
                .map(ChatMessageResponseDTO::from)
                .toList();
    }

    /**
     * 채팅방의 특정 타입 미디어 조회
     */
    public List<ChatMessageResponseDTO> getMediaFilesByType(String roomId, MessageType type) {
        validateRoomExists(roomId);

        List<ChatMessage> mediaMessages = chatMessageRepository
                .findByRoomIdAndMessageTypeOrderBySentAtDesc(roomId, type);

        return mediaMessages.stream()
                .map(ChatMessageResponseDTO::from)
                .toList();
    }

    /**
     * 마지막 메시지 텍스트 결정
     */
    private String resolveLastMessage(ChatMessageRequestDTO request) {
        // 텍스트 내용이 있으면 그대로 사용
        if (request.getContent() != null && !request.getContent().isBlank()) {
            return request.getContent();
        }

        // 파일 타입별 기본 메시지
        return switch (request.getMessageType()) {
            case IMAGE -> "사진을 보냈습니다.";
            case VIDEO -> "동영상을 보냈습니다.";
            case FILE -> "파일을 보냈습니다.";
            default -> "";
        };
    }

    /**
     * 채팅방 존재 확인
     */
    private void validateRoomExists(String roomId) {
        if (!chatRoomRepository.existsById(roomId)) {
            throw new ChatRoomNotFoundException(roomId);
        }
    }
}
