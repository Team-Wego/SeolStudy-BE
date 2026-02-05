package com.wego.seolstudybe.task.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TaskStatusUpdateRequest {

    @NotNull(message = "상태 값은 필수입니다.")
    private Boolean isChecked;
}
