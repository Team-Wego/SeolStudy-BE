package com.wego.seolstudybe.chat.entity;

import com.wego.seolstudybe.chat.entity.enums.MessageType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * MongoDB 채팅 메시지 엔티티
 */
@Document(collection = "chat_messages")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessage {

    @Id
    private String id;

    @Indexed
    private String roomId;

    private Long senderId;

    private MessageType messageType;

    private String content;

    private String fileUrl;

    private String fileName;

    private boolean isRead;

    private LocalDateTime readAt;

    private LocalDateTime sentAt;

    @Builder
    public ChatMessage(String roomId, Long senderId, MessageType messageType,
                       String content, String fileUrl, String fileName) {
        this.roomId = roomId;
        this.senderId = senderId;
        this.messageType = messageType;
        this.content = content;
        this.fileUrl = fileUrl;
        this.fileName = fileName;
        this.isRead = false;
        this.sentAt = LocalDateTime.now();
    }

    public void markAsRead() {
        this.isRead = true;
        this.readAt = LocalDateTime.now();
    }
}
