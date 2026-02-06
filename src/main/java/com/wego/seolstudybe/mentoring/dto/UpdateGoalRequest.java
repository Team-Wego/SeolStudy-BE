package com.wego.seolstudybe.mentoring.dto;

import com.wego.seolstudybe.mentoring.entity.enums.Subject;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UpdateGoalRequest {
    @Schema(description = "목표명", defaultValue = "국어 문법 복습지")
    @NotBlank
    private String name;

    @Schema(description = "과목")
    @NotNull
    private Subject subject;

    @Schema(description = "학습지 파일 변경 여부")
    private boolean worksheetChanged;
}