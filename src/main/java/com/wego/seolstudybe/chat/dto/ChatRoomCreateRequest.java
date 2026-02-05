package com.wego.seolstudybe.chat.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatRoomCreateRequest {

    @NotNull(message = "멘토 ID는 필수입니다")
    private Long mentorId;

    @NotNull(message = "멘티 ID는 필수입니다")
    private Long menteeId;
}
