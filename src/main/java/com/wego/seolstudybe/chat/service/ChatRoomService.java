package com.wego.seolstudybe.chat.service;

import com.wego.seolstudybe.chat.dto.ChatRoomResponse;
import com.wego.seolstudybe.chat.entity.ChatRoom;
import com.wego.seolstudybe.chat.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    /**
     * 채팅방 생성 또는 기존 채팅방 반환
     */
    public ChatRoomResponse createOrGetChatRoom(Long mentorId, Long menteeId) {
        // 기존 채팅방이 있으면 반환
        Optional<ChatRoom> existingRoom = chatRoomRepository.findByMentorIdAndMenteeId(mentorId, menteeId);

        if (existingRoom.isPresent()) {
            return ChatRoomResponse.from(existingRoom.get());
        }

        // 없으면 새로 생성
        ChatRoom newRoom = ChatRoom.builder()
                .mentorId(mentorId)
                .menteeId(menteeId)
                .build();

        ChatRoom savedRoom = chatRoomRepository.save(newRoom);
        return ChatRoomResponse.from(savedRoom);
    }

    /**
     * 채팅방 단건 조회
     */
    public ChatRoomResponse getChatRoom(String roomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("채팅방을 찾을 수 없습니다: " + roomId));
        return ChatRoomResponse.from(chatRoom);
    }

    /**
     * 멘토의 채팅방 목록 조회
     */
    public List<ChatRoomResponse> getMentorChatRooms(Long mentorId) {
        return chatRoomRepository.findByMentorIdOrderByLastMessageAtDesc(mentorId)
                .stream()
                .map(ChatRoomResponse::from)
                .toList();
    }

    /**
     * 멘티의 채팅방 목록 조회
     */
    public List<ChatRoomResponse> getMenteeChatRooms(Long menteeId) {
        return chatRoomRepository.findByMenteeIdOrderByLastMessageAtDesc(menteeId)
                .stream()
                .map(ChatRoomResponse::from)
                .toList();
    }

    /**
     * 멘토-멘티 간 기존 채팅방 조회
     */
    public Optional<ChatRoomResponse> findExistingChatRoom(Long mentorId, Long menteeId) {
        return chatRoomRepository.findByMentorIdAndMenteeId(mentorId, menteeId)
                .map(ChatRoomResponse::from);
    }
}
