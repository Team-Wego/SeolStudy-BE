package com.wego.seolstudybe.task.dto;

import com.wego.seolstudybe.mentoring.entity.enums.Subject;
import com.wego.seolstudybe.task.entity.enums.TaskType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class TaskResponse {

    private int taskId;
    private int menteeId;
    private String title;
    private TaskType type;
    private LocalDate date;
    private Subject subject;
}

