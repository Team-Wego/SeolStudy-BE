package com.wego.seolstudybe.chat.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * MongoDB 채팅방 엔티티
 * @Document는 JPA의 @Entity와 같은 역할 (MongoDB 컬렉션에 매핑)
 */
@Document(collection = "chat_rooms")
@CompoundIndex(name = "mentor_mentee_idx", def = "{'mentorId': 1, 'menteeId': 1}", unique = true)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoom {

    @Id
    private String id;

    private Long mentorId;

    private Long menteeId;

    private String lastMessage;

    private Long lastSenderId;

    private LocalDateTime lastMessageAt;

    private int mentorUnreadCount;

    private int menteeUnreadCount;

    private LocalDateTime createdAt;

    @Builder
    public ChatRoom(Long mentorId, Long menteeId) {
        this.mentorId = mentorId;
        this.menteeId = menteeId;
        this.mentorUnreadCount = 0;
        this.menteeUnreadCount = 0;
        this.createdAt = LocalDateTime.now();
    }

    public void updateLastMessage(String content, Long senderId) {
        this.lastMessage = content;
        this.lastSenderId = senderId;
        this.lastMessageAt = LocalDateTime.now();
    }

    public void incrementUnreadCount(Long senderId) {
        if (senderId.equals(mentorId)) {
            this.menteeUnreadCount++;
        } else {
            this.mentorUnreadCount++;
        }
    }

    public void resetUnreadCount(Long readerId) {
        if (readerId.equals(mentorId)) {
            this.mentorUnreadCount = 0;
        } else {
            this.menteeUnreadCount = 0;
        }
    }
}
