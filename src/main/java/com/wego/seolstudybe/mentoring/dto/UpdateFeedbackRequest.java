package com.wego.seolstudybe.mentoring.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class UpdateFeedbackRequest {
    @Schema(description = "피드백 내용", defaultValue = "이번 주 플래너 정리가 잘 되어 있어요.")
    @NotBlank
    private String content;

    @Schema(description = "첨부 파일 변경 여부")
    private boolean imageChanged;

    @Schema(description = "삭제된 첨부 파일 ID(PK) 리스트")
    private List<Integer> deletedImageIds;
}