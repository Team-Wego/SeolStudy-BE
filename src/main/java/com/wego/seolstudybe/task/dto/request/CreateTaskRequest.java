package com.wego.seolstudybe.task.dto.request;

import com.wego.seolstudybe.mentoring.entity.enums.Subject;
import com.wego.seolstudybe.task.entity.enums.TaskType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
public class CreateTaskRequest {

    @NotBlank
    private String title;

    private String description;

    @NotNull
    private TaskType type; // TODO, ASSIGNMENT

    @NotNull
    private LocalDate date;

    @NotNull
    private Subject subject; // KOR, ENG, MATH

    private Integer goalId;

    private List<Integer> worksheetFileIds;
}

