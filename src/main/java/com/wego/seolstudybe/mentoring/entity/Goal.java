package com.wego.seolstudybe.mentoring.entity;

import com.wego.seolstudybe.member.entity.Member;
import com.wego.seolstudybe.mentoring.entity.enums.Subject;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "goal")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worksheet_id")
    private WorksheetFile worksheetFile;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Subject subject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", nullable = false)
    private Member creator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_mentee_id", nullable = false)
    private Member targetMentee;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public Goal(final WorksheetFile worksheetFile, final String name, final Subject subject, final Member creator,
                final Member targetMentee) {
        this.worksheetFile = worksheetFile;
        this.name = name;
        this.subject = subject;
        this.creator = creator;
        this.targetMentee = targetMentee;
    }

    public void softDelete(final LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public void updateGoal(final String name, final Subject subject, final WorksheetFile worksheetFile) {
        this.name = name;
        this.subject = subject;
        this.worksheetFile = worksheetFile;
    }
}