package com.wego.seolstudybe.mentoring.service;

import com.wego.seolstudybe.mentoring.dto.CreateFeedbackRequest;
import com.wego.seolstudybe.mentoring.dto.DailyFeedbackCountResponse;
import com.wego.seolstudybe.mentoring.dto.FeedbackListResponse;
import com.wego.seolstudybe.mentoring.dto.FeedbackResponse;
import com.wego.seolstudybe.mentoring.entity.Feedback;
import com.wego.seolstudybe.mentoring.entity.enums.FeedbackType;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

public interface FeedbackService {
    Feedback createFeedback(final int mentorId, final CreateFeedbackRequest request, final List<MultipartFile> files);

    FeedbackResponse getFeedback(final int memberId, final int feedbackId);

    List<FeedbackListResponse> getFeedbackList(final int memberId, final int menteeId, final FeedbackType type);

    List<DailyFeedbackCountResponse> getDailyFeedbackCount(final int memberId, final int menteeId,
                                                           final LocalDate startDate, final LocalDate endDate);

    FeedbackResponse getPlannerFeedback(final int memberId, final Integer menteeId, final LocalDate date);
}