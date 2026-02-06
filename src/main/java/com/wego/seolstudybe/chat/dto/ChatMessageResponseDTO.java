package com.wego.seolstudybe.chat.dto;

import com.wego.seolstudybe.chat.entity.ChatMessage;
import com.wego.seolstudybe.chat.entity.enums.MessageType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ChatMessageResponseDTO {

    private String messageId;
    private String roomId;
    private Long senderId;
    private MessageType messageType;
    private String content;
    private String fileUrl;
    private String fileName;
    private boolean isRead;
    private LocalDateTime sentAt;

    public static ChatMessageResponseDTO from(ChatMessage message) {
        return ChatMessageResponseDTO.builder()
                .messageId(message.getId())
                .roomId(message.getRoomId())
                .senderId(message.getSenderId())
                .messageType(message.getMessageType())
                .content(message.getContent())
                .fileUrl(message.getFileUrl())
                .fileName(message.getFileName())
                .isRead(message.isRead())
                .sentAt(message.getSentAt())
                .build();
    }
}
