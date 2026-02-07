package com.wego.seolstudybe.task.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class PlannerCommentCreateRequest {

    @NotNull(message = "날짜는 필수입니다.")
    private LocalDate date;

    @NotBlank(message = "코멘트는 필수입니다.")
    @Size(max = 2000, message = "코멘트는 2000자 이하로 입력해주세요.")
    private String comment;
}
