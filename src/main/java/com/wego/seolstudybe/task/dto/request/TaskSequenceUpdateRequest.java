package com.wego.seolstudybe.task.dto.request;

import jakarta.validation.constraints.NotNull;

public record TaskSequenceUpdateRequest(
        @NotNull(message = "순서 값은 필수입니다.")
        Integer sequence
) {
}
