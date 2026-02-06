package com.wego.seolstudybe.notification.entity;

import com.wego.seolstudybe.notification.entity.enums.NotificationType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "notification")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType type;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String body;

    @Column(name = "receiver_id", nullable = false)
    private Long receiverId;

    @Column(name = "is_read", nullable = false)
    private boolean isRead = false;

    @Column(columnDefinition = "TEXT")
    private String data;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Builder
    public Notification(NotificationType type, String title, String body, Long receiverId, String data) {
        this.type = type;
        this.title = title;
        this.body = body;
        this.receiverId = receiverId;
        this.data = data;
    }

    public void markAsRead() {
        this.isRead = true;
    }
}
