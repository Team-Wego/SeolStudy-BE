package com.wego.seolstudybe.task.dto.response;

import com.wego.seolstudybe.mentoring.entity.enums.Subject;
import com.wego.seolstudybe.task.entity.Task;
import com.wego.seolstudybe.task.entity.enums.TaskType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class TaskResponse {

    private final int id;
    private final String title;
    private final String description;
    private final TaskType type;
    private final LocalDate date;
    private final Subject subject;
    private final boolean isChecked;
    private final LocalDateTime checkedAt;
    private final Integer sequence;
    private final LocalDateTime createdAt;

    public static TaskResponse from(Task task) {
        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .type(task.getType())
                .date(task.getDate())
                .subject(task.getSubject())
                .isChecked(task.isChecked())
                .checkedAt(task.getCheckedAt())
                .sequence(task.getSequence())
                .createdAt(task.getCreatedAt())
                .build();
    }
}
