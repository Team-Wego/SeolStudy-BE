package com.wego.seolstudybe.task.entity;

import com.wego.seolstudybe.mentoring.entity.WorksheetFile;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "task_worksheet")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class TaskWorksheet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id", nullable = false)
    private WorksheetFile worksheetFile;
}
