package com.wego.seolstudybe.task.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class StudyTimeUpdateRequest {

    @NotNull(message = "시작 시간은 필수입니다.")
    private LocalDateTime startedAt;

    @NotNull(message = "종료 시간은 필수입니다.")
    private LocalDateTime endedAt;
}
