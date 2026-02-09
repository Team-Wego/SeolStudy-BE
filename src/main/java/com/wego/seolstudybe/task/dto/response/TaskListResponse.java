package com.wego.seolstudybe.task.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wego.seolstudybe.mentoring.entity.enums.Subject;
import com.wego.seolstudybe.task.entity.enums.TaskType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class TaskListResponse {

    private int id;
    private String title;
    private TaskType type;
    private Subject subject;
    private LocalDate date;
    @JsonProperty("isChecked")
    private boolean isChecked;
    private boolean hasFeedback;
    private Integer sequence;
    private Integer goalId;
    private String goalName;
}
