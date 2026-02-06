package com.wego.seolstudybe.chat.service;

import com.wego.seolstudybe.chat.dto.ChatMessageRequest;
import com.wego.seolstudybe.chat.dto.ChatMessageResponse;
import com.wego.seolstudybe.chat.entity.ChatMessage;
import com.wego.seolstudybe.chat.entity.ChatRoom;
import com.wego.seolstudybe.chat.entity.enums.MessageType;
import com.wego.seolstudybe.chat.repository.ChatMessageRepository;
import com.wego.seolstudybe.chat.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;

    /**
     * 메시지 저장
     */
    public ChatMessageResponse saveMessage(ChatMessageRequest request) {
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
        chatRoomRepository.findById(request.getRoomId())
                .ifPresent(room -> {
                    room.updateLastMessage(request.getContent(), request.getSenderId());
                    room.incrementUnreadCount(request.getSenderId());
                    chatRoomRepository.save(room);
                });

        return ChatMessageResponse.from(savedMessage);
    }

    /**
     * 채팅방 메시지 이력 조회 (페이징)
     */
    public List<ChatMessageResponse> getMessages(String roomId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ChatMessage> messages = chatMessageRepository.findByRoomIdOrderBySentAtDesc(roomId, pageable);

        return messages.getContent().stream()
                .map(ChatMessageResponse::from)
                .toList();
    }

    /**
     * 메시지 읽음 처리
     */
    public void markMessagesAsRead(String roomId, Long readerId) {
        List<ChatMessage> unreadMessages = chatMessageRepository
                .findByRoomIdAndIsReadFalseAndSenderIdNot(roomId, readerId);

        unreadMessages.forEach(ChatMessage::markAsRead);
        chatMessageRepository.saveAll(unreadMessages);

        // 채팅방 읽지 않은 메시지 수 초기화
        chatRoomRepository.findById(roomId)
                .ifPresent(room -> {
                    room.resetUnreadCount(readerId);
                    chatRoomRepository.save(room);
                });
    }

    /**
     * 읽지 않은 메시지 수 조회
     */
    public long getUnreadCount(String roomId, Long userId) {
        return chatMessageRepository.countByRoomIdAndIsReadFalseAndSenderIdNot(roomId, userId);
    }

    /**
     * 채팅방의 모든 미디어/파일 조회 (이미지, 동영상, 파일)
     */
    public List<ChatMessageResponse> getMediaFiles(String roomId) {
        List<MessageType> mediaTypes = List.of(MessageType.IMAGE, MessageType.VIDEO, MessageType.FILE);
        List<ChatMessage> mediaMessages = chatMessageRepository
                .findByRoomIdAndMessageTypeInOrderBySentAtDesc(roomId, mediaTypes);

        return mediaMessages.stream()
                .map(ChatMessageResponse::from)
                .toList();
    }

    /**
     * 채팅방의 특정 타입 미디어 조회
     */
    public List<ChatMessageResponse> getMediaFilesByType(String roomId, MessageType type) {
        List<ChatMessage> mediaMessages = chatMessageRepository
                .findByRoomIdAndMessageTypeOrderBySentAtDesc(roomId, type);

        return mediaMessages.stream()
                .map(ChatMessageResponse::from)
                .toList();
    }
}
