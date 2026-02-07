package com.wego.seolstudybe.mentoring.dto;

import com.wego.seolstudybe.mentoring.entity.FeedbackImage;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FeedbackImageResponse {
    private int imageId;

    private String imageUrl;

    public static FeedbackImageResponse of(final FeedbackImage feedbackImage) {
        return FeedbackImageResponse.builder()
                .imageId(feedbackImage.getId())
                .imageUrl(feedbackImage.getUrl())
                .build();
    }
}