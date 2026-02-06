package com.wego.seolstudybe.notification.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FcmTokenRequest {

    @NotNull(message = "회원 ID는 필수입니다")
    private Long memberId;

    @NotBlank(message = "FCM 토큰은 필수입니다")
    private String token;
}
