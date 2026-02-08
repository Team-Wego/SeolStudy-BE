package com.wego.seolstudybe.task.dto.response;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DailyTaskStatusResponse {
    @Schema(description = "해당 날짜")
    private LocalDate date;

    @Schema(description = "총 과제 개수")
    private int totalTaskCount;

    @Schema(description = "총 완료된 과제 개수")
    private int completedTaskCount;
}