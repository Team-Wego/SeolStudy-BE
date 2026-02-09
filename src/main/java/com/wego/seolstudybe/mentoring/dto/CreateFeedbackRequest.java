package com.wego.seolstudybe.mentoring.dto;

import com.wego.seolstudybe.mentoring.entity.enums.FeedbackType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
public class CreateFeedbackRequest {
    @Schema(description = "멘티 ID(PK)")
    @NotNull
    private Integer menteeId;

    @Schema(description = "피드백 구분 (PLANNER, TASK, WEEKLY, MONTHLY)")
    @NotNull
    private FeedbackType type;

    @Schema(description = "피드백 내용", defaultValue = "이번 주 플래너 정리가 잘 되어 있어요.")
    @NotBlank
    private String content;

    @Schema(description = "피드백 대상 날짜")
    @NotNull
    private LocalDate targetDate;

    @Schema(description = "과제 ID (피드백 구분이 TASK일 때)")
    private Integer taskId;

    @Schema(description = "강조하고 싶은 내용(형광펜)")
    private String highlight;
}