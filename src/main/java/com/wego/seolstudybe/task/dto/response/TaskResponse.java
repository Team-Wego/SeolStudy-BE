package com.wego.seolstudybe.task.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("isChecked")
    private final boolean isChecked;
    private final LocalDateTime checkedAt;
    private final String comment;
    private final LocalDateTime submittedAt;
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
                .comment(task.getComment())
                .submittedAt(task.getSubmittedAt())
                .sequence(task.getSequence())
                .createdAt(task.getCreatedAt())
                .build();
    }
}
