package com.wego.seolstudybe.mentoring.dto;

import com.wego.seolstudybe.mentoring.entity.Feedback;
import com.wego.seolstudybe.mentoring.entity.enums.FeedbackType;
import com.wego.seolstudybe.mentoring.entity.enums.Subject;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class FeedbackListResponse {
    private int feedbackId;
    private FeedbackType feedbackType;
    private Subject subject;
    private String taskTitle;
    private LocalDateTime submittedAt;
    private LocalDateTime createdAt;
    private String content;

    public static FeedbackListResponse of(final Feedback feedback) {
        return FeedbackListResponse.builder()
                .feedbackId(feedback.getId())
                .feedbackType(feedback.getType())
                .subject(feedback.getTask() != null ? feedback.getTask().getSubject() : null)
                .taskTitle(feedback.getTask() != null ? feedback.getTask().getTitle() : null)
                .submittedAt(feedback.getTask() != null ? feedback.getTask().getSubmittedAt() : null)
                .createdAt(feedback.getCreatedAt())
                .content(feedback.getContent())
                .build();
    }
}