package com.wego.seolstudybe.task.dto.request;

import com.wego.seolstudybe.mentoring.entity.enums.Subject;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record TaskCreateRequest(
        @NotBlank(message = "할 일 제목은 필수입니다.")
        String title,

        @NotNull(message = "날짜는 필수입니다.")
        LocalDate date,

        @NotNull(message = "과목은 필수입니다.")
        Subject subject
) {
}
