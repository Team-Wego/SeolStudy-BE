package com.wego.seolstudybe.task.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class GetPlannerCommentDto {

    private int id;
    private LocalDate date;
    private String comment;
    private LocalDateTime completedAt;
}
