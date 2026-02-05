package com.wego.seolstudybe.task.dto.request;

import jakarta.validation.constraints.NotNull;

public record TaskStatusUpdateRequest(
        @NotNull(message = "상태 값은 필수입니다.")
        Boolean isChecked
) {
}
