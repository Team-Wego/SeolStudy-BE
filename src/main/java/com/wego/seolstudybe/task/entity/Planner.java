package com.wego.seolstudybe.task.entity;

import com.wego.seolstudybe.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "planner", uniqueConstraints = {
        @UniqueConstraint(name = "unique_planner_date", columnNames = {"mentee_id", "date"})
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class Planner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mentee_id", nullable = false)
    private Member mentee;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false, length = 2000)
    private String comment;
}