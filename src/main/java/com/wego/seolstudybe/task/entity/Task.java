package com.wego.seolstudybe.task.entity;

import com.wego.seolstudybe.member.entity.Member;
import com.wego.seolstudybe.mentoring.entity.Goal;
import com.wego.seolstudybe.mentoring.entity.enums.Subject;
import com.wego.seolstudybe.task.entity.enums.TaskType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "task")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mentee_id", nullable = false)
    private Member mentee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goal_id")
    private Goal goal;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskType type;

    @Column(nullable = false)
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Subject subject;

    @Column(name = "is_checked", nullable = false)
    @Builder.Default
    private boolean isChecked = false;

    @Column(name = "checked_at")
    private LocalDateTime checkedAt;

    @Column(length = 1000)
    private String comment;

    @Column(name = "submitted_at")
    private LocalDateTime submittedAt;

    @Column(name = "has_feedback", nullable = false)
    @Builder.Default
    private boolean hasFeedback = false;

    private Integer sequence;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public void updateFeedbackStatus( final boolean status) {
        this.hasFeedback = status;
    }

    public void updateContent(String title, Subject subject) {
        this.title = title;
        this.subject = subject;
    }

    public void updateSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public void changeStatus(boolean isChecked) {
        this.isChecked = isChecked;
        this.checkedAt = isChecked ? LocalDateTime.now() : null;
    }

    public void update(
            String title,
            String description,
            TaskType type,
            LocalDate date,
            Subject subject,
            Goal goal
    ) {
        this.title = title;
        this.description = description;
        this.type = type;
        this.date = date;
        this.subject = subject;
        this.goal = goal;
    }

}