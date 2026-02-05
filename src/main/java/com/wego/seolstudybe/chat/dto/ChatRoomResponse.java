package com.wego.seolstudybe.chat.dto;

import com.wego.seolstudybe.chat.entity.ChatRoom;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ChatRoomResponse {

    private String roomId;
    private Long mentorId;
    private Long menteeId;
    private String lastMessage;
    private Long lastSenderId;
    private LocalDateTime lastMessageAt;
    private int unreadCount;
    private LocalDateTime createdAt;

    public static ChatRoomResponse from(ChatRoom chatRoom) {
        return ChatRoomResponse.builder()
                .roomId(chatRoom.getId())
                .mentorId(chatRoom.getMentorId())
                .menteeId(chatRoom.getMenteeId())
                .lastMessage(chatRoom.getLastMessage())
                .lastSenderId(chatRoom.getLastSenderId())
                .lastMessageAt(chatRoom.getLastMessageAt())
                .createdAt(chatRoom.getCreatedAt())
                .build();
    }

    public static ChatRoomResponse from(ChatRoom chatRoom, Long currentUserId) {
        int unread = currentUserId.equals(chatRoom.getMentorId())
                ? chatRoom.getMentorUnreadCount()
                : chatRoom.getMenteeUnreadCount();

        return ChatRoomResponse.builder()
                .roomId(chatRoom.getId())
                .mentorId(chatRoom.getMentorId())
                .menteeId(chatRoom.getMenteeId())
                .lastMessage(chatRoom.getLastMessage())
                .lastSenderId(chatRoom.getLastSenderId())
                .lastMessageAt(chatRoom.getLastMessageAt())
                .unreadCount(unread)
                .createdAt(chatRoom.getCreatedAt())
                .build();
    }
}
