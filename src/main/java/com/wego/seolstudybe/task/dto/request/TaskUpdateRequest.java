package com.wego.seolstudybe.task.dto.request;

import com.wego.seolstudybe.mentoring.entity.enums.Subject;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TaskUpdateRequest(
        @NotBlank(message = "할 일 제목은 필수입니다.")
        String title,

        @NotNull(message = "과목은 필수입니다.")
        Subject subject
) {
}
