package com.wego.seolstudybe.mentoring.dto;

import com.wego.seolstudybe.mentoring.entity.Feedback;
import com.wego.seolstudybe.mentoring.entity.enums.FeedbackType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class FeedbackResponse {
    private int feedbackId;

    private int mentorId;

    private String mentorName;

    private int menteeId;

    private String menteeName;

    private String content;

    private FeedbackType feedbackType;

    private LocalDateTime createdAt;

    private Integer taskId;

    private LocalDate targetDate;

    private List<FeedbackImageResponse> feedbackImages;

    public static FeedbackResponse of(final Feedback feedback, final List<FeedbackImageResponse> feedbackImages) {
        return FeedbackResponse.builder()
                .feedbackId(feedback.getId())
                .menteeId(feedback.getMentee().getId())
                .menteeName(feedback.getMentee().getName())
                .content(feedback.getContent())
                .mentorId(feedback.getMentor().getId())
                .mentorName(feedback.getMentor().getName())
                .createdAt(feedback.getCreatedAt())
                .feedbackType(feedback.getType())
                .taskId((feedback.getTask() == null) ? null : feedback.getTask().getId())
                .targetDate(feedback.getTargetDate())
                .feedbackImages(feedbackImages)
                .build();
    }
}