package com.wego.seolstudybe.chat.dto;

import com.wego.seolstudybe.chat.entity.ChatRoom;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ChatRoomResponseDTO {

    private String roomId;
    private Long mentorId;
    private Long menteeId;
    private String mentorName;
    private String menteeName;
    private String mentorProfileUrl;
    private String menteeProfileUrl;
    private String lastMessage;
    private Long lastSenderId;
    private LocalDateTime lastMessageAt;
    private int mentorUnreadCount;
    private int menteeUnreadCount;
    private LocalDateTime createdAt;

    public static ChatRoomResponseDTO from(ChatRoom chatRoom) {
        return ChatRoomResponseDTO.builder()
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

    public static ChatRoomResponseDTO from(ChatRoom chatRoom, String mentorName, String menteeName,
                                              String mentorProfileUrl, String menteeProfileUrl) {
        return ChatRoomResponseDTO.builder()
                .roomId(chatRoom.getId())
                .mentorId(chatRoom.getMentorId())
                .menteeId(chatRoom.getMenteeId())
                .mentorName(mentorName)
                .menteeName(menteeName)
                .mentorProfileUrl(mentorProfileUrl)
                .menteeProfileUrl(menteeProfileUrl)
                .lastMessage(chatRoom.getLastMessage())
                .lastSenderId(chatRoom.getLastSenderId())
                .lastMessageAt(chatRoom.getLastMessageAt())
                .mentorUnreadCount(chatRoom.getMentorUnreadCount())
                .menteeUnreadCount(chatRoom.getMenteeUnreadCount())
                .createdAt(chatRoom.getCreatedAt())
                .build();
    }
}
