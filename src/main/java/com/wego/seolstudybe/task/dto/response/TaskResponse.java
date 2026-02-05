package com.wego.seolstudybe.task.dto.response;

import com.wego.seolstudybe.mentoring.entity.enums.Subject;
import com.wego.seolstudybe.task.entity.Task;
import com.wego.seolstudybe.task.entity.enums.TaskType;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record TaskResponse(
        int id,
        String title,
        String description,
        TaskType type,
        LocalDate date,
        Subject subject,
        boolean isChecked,
        LocalDateTime checkedAt,
        Integer sequence,
        LocalDateTime createdAt
) {
    public static TaskResponse from(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getType(),
                task.getDate(),
                task.getSubject(),
                task.isChecked(),
                task.getCheckedAt(),
                task.getSequence(),
                task.getCreatedAt()
        );
    }
}
