package com.wego.seolstudybe.task.dto.request;

import com.wego.seolstudybe.mentoring.entity.enums.Subject;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class TaskCreateRequest {

    @NotBlank(message = "할 일 제목은 필수입니다.")
    private String title;

    @NotNull(message = "날짜는 필수입니다.")
    private LocalDate date;

    @NotNull(message = "과목은 필수입니다.")
    private Subject subject;
}
