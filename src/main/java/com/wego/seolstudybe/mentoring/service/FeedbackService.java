package com.wego.seolstudybe.mentoring.service;

import com.wego.seolstudybe.mentoring.dto.CreateFeedbackRequest;
import com.wego.seolstudybe.mentoring.dto.FeedbackResponse;
import com.wego.seolstudybe.mentoring.entity.Feedback;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FeedbackService {
    Feedback createFeedback(final int mentorId, final CreateFeedbackRequest request, final List<MultipartFile> files);

    FeedbackResponse getFeedback(final int memberId, final int feedbackId);
}