package com.wego.seolstudybe.mentoring.dto;

import com.wego.seolstudybe.mentoring.entity.Feedback;
import com.wego.seolstudybe.mentoring.entity.enums.FeedbackType;
import com.wego.seolstudybe.mentoring.entity.enums.Subject;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class FeedbackListResponse {
    private int feedbackId;
    private FeedbackType feedbackType;
    private Integer taskId;
    private Subject subject;
    private String goalName;
    private String taskTitle;
    private LocalDateTime submittedAt;
    private LocalDateTime createdAt;
    private LocalDate targetDate;
    private String content;
    private String highlight;
    private List<FeedbackImageResponse> feedbackImages;

    public static FeedbackListResponse of(final Feedback feedback, final List<FeedbackImageResponse> feedbackImages) {
        return FeedbackListResponse.builder()
                .feedbackId(feedback.getId())
                .feedbackType(feedback.getType())
                .taskId(feedback.getTask() != null ? feedback.getTask().getId() : null)
                .subject(feedback.getTask() != null ? feedback.getTask().getSubject() : null)
                .goalName(feedback.getTask() != null && feedback.getTask().getGoal() != null ? feedback.getTask().getGoal().getName() : null)
                .taskTitle(feedback.getTask() != null ? feedback.getTask().getTitle() : null)
                .submittedAt(feedback.getTask() != null ? feedback.getTask().getSubmittedAt() : null)
                .createdAt(feedback.getCreatedAt())
                .targetDate(feedback.getTargetDate())
                .content(feedback.getContent())
                .highlight(feedback.getHighlight())
                .feedbackImages(feedbackImages)
                .build();
    }
}