package com.wego.seolstudybe.chat.service;

import com.wego.seolstudybe.chat.dto.ChatRoomResponseDTO;
import com.wego.seolstudybe.chat.entity.ChatRoom;
import com.wego.seolstudybe.chat.repository.ChatRoomRepository;
import com.wego.seolstudybe.common.error.ErrorCode;
import com.wego.seolstudybe.chat.exception.ChatRoomNotFoundException;
import com.wego.seolstudybe.chat.exception.InvalidChatException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    /**
     * 채팅방 생성 또는 기존 채팅방 반환
     */
    public ChatRoomResponseDTO createOrGetChatRoom(Long mentorId, Long menteeId) {
        // 같은 사용자끼리 채팅방 생성 방지
        if (mentorId.equals(menteeId)) {
            throw new InvalidChatException(ErrorCode.SAME_USER_CHAT_NOT_ALLOWED);
        }

        // 기존 채팅방이 있으면 반환
        Optional<ChatRoom> existingRoom = chatRoomRepository.findByMentorIdAndMenteeId(mentorId, menteeId);

        if (existingRoom.isPresent()) {
            log.info("기존 채팅방 반환: roomId={}", existingRoom.get().getId());
            return ChatRoomResponseDTO.from(existingRoom.get());
        }

        // 없으면 새로 생성
        ChatRoom newRoom = ChatRoom.builder()
                .mentorId(mentorId)
                .menteeId(menteeId)
                .build();

        ChatRoom savedRoom = chatRoomRepository.save(newRoom);
        log.info("새 채팅방 생성: roomId={}, mentorId={}, menteeId={}", savedRoom.getId(), mentorId, menteeId);
        return ChatRoomResponseDTO.from(savedRoom);
    }

    /**
     * 채팅방 단건 조회
     */
    public ChatRoomResponseDTO getChatRoom(String roomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new ChatRoomNotFoundException(roomId));
        return ChatRoomResponseDTO.from(chatRoom);
    }

    /**
     * 멘토의 채팅방 목록 조회
     */
    public List<ChatRoomResponseDTO> getMentorChatRooms(Long mentorId) {
        return chatRoomRepository.findByMentorIdOrderByLastMessageAtDesc(mentorId)
                .stream()
                .map(ChatRoomResponseDTO::from)
                .toList();
    }

    /**
     * 멘티의 채팅방 목록 조회
     */
    public List<ChatRoomResponseDTO> getMenteeChatRooms(Long menteeId) {
        return chatRoomRepository.findByMenteeIdOrderByLastMessageAtDesc(menteeId)
                .stream()
                .map(ChatRoomResponseDTO::from)
                .toList();
    }

    /**
     * 멘토-멘티 간 기존 채팅방 조회
     */
    public Optional<ChatRoomResponseDTO> findExistingChatRoom(Long mentorId, Long menteeId) {
        return chatRoomRepository.findByMentorIdAndMenteeId(mentorId, menteeId)
                .map(ChatRoomResponseDTO::from);
    }
}
