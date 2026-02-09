package com.wego.seolstudybe.task.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TaskCommentUpdateRequest {

    @NotBlank(message = "코멘트는 필수입니다.")
    @Size(max = 1000, message = "코멘트는 1000자 이하로 입력해주세요.")
    private String comment;
}
