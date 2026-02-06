package com.wego.seolstudybe.mentoring.dto;

import com.wego.seolstudybe.mentoring.entity.enums.Subject;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CreateGoalRequest {
    @Schema(description = "목표명", defaultValue = "국어 문법 복습지")
    @NotBlank
    private String name;

    @Schema(description = "과목")
    @NotNull
    private Subject subject;

    @Schema(description = "목표 부여할 멘티 ID(PK)")
    private int menteeId;
}