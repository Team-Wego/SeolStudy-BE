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
    private int mentorUnreadCount;
    private int menteeUnreadCount;
    private LocalDateTime createdAt;

    public static ChatRoomResponse from(ChatRoom chatRoom) {
        return ChatRoomResponse.builder()
                .roomId(chatRoom.getId())
                .mentorId(chatRoom.getMentorId())
                .menteeId(chatRoom.getMenteeId())
                .lastMessage(chatRoom.getLastMessage())
                .lastSenderId(chatRoom.getLastSenderId())
                .lastMessageAt(chatRoom.getLastMessageAt())
                .mentorUnreadCount(chatRoom.getMentorUnreadCount())
                .menteeUnreadCount(chatRoom.getMenteeUnreadCount())
                .createdAt(chatRoom.getCreatedAt())
                .build();
    }
}
