package com.wego.seolstudybe.task.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class GetPlannerCommentDto {

    private int id;
    private LocalDate date;
    private String comment;
}
