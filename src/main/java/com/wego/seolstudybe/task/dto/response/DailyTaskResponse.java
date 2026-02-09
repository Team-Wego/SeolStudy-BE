package com.wego.seolstudybe.task.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wego.seolstudybe.mentoring.entity.enums.Subject;
import com.wego.seolstudybe.task.entity.enums.TaskType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class DailyTaskResponse {

    private int id;
    private String title;
    private Subject subject;
    private TaskType type;
    @JsonProperty("isChecked")
    private boolean isChecked;
    private boolean hasFeedback;
    private Integer sequence;
    private int totalMinutes;
    private Integer goalId;
    private String goalName;
    private LocalDateTime submittedAt;
}
