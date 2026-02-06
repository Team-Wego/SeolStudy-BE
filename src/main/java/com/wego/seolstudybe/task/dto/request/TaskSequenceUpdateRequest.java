package com.wego.seolstudybe.task.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TaskSequenceUpdateRequest {

    @NotNull(message = "순서 값은 필수입니다.")
    private Integer sequence;
}
