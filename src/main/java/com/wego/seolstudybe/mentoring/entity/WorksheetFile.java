package com.wego.seolstudybe.mentoring.entity;

import com.wego.seolstudybe.member.entity.Member;
import com.wego.seolstudybe.mentoring.entity.enums.Subject;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "worksheet_file")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class WorksheetFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mentee_id", nullable = false)
    private Member mentee;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private Float size;

    @Column(nullable = false)
    private String type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Subject subject;

    public void updateSubject(final Subject subject) {
        this.subject = subject;
    }

    public WorksheetFile(final Member mentee, final String name, final String url, final Float size, final String type,
                         final Subject subject) {
        this.mentee = mentee;
        this.name = name;
        this.url = url;
        this.size = size;
        this.type = type;
        this.subject = subject;
    }
}