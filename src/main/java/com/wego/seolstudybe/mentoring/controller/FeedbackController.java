package com.wego.seolstudybe.mentoring.controller;

import com.wego.seolstudybe.mentoring.dto.CreateFeedbackRequest;
import com.wego.seolstudybe.mentoring.dto.DailyFeedbackCountResponse;
import com.wego.seolstudybe.mentoring.dto.FeedbackListResponse;
import com.wego.seolstudybe.mentoring.dto.FeedbackResponse;
import com.wego.seolstudybe.mentoring.entity.Feedback;
import com.wego.seolstudybe.mentoring.entity.enums.FeedbackType;
import com.wego.seolstudybe.mentoring.service.FeedbackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "피드백 - Feedback", description = "피드백 관련 API")
@RequiredArgsConstructor
@RestController
public class FeedbackController {
    private final FeedbackService feedbackService;

    @Operation(summary = "피드백 등록")
    @PostMapping(value = "/mentors/feedback", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Integer> createFeedback(@CookieValue("memberId") final int memberId,
                                                  @Valid @RequestPart(name = "request") final CreateFeedbackRequest request,
                                                  @RequestPart(name = "files", required = false) final List<MultipartFile> files) {
        final Feedback feedback = feedbackService.createFeedback(memberId, request, files);

        return ResponseEntity.ok(feedback.getId());
    }

    @Operation(summary = "피드백 분류별 목록 조회")
    @GetMapping("/feedback")
    public ResponseEntity<List<FeedbackListResponse>> getFeedbackList(@CookieValue("memberId") final int memberId,
                                                                      @RequestParam("menteeId") final int menteeId,
                                                                      @RequestParam(value = "type", required = false) final FeedbackType type) {
        final List<FeedbackListResponse> response = feedbackService.getFeedbackList(memberId, menteeId, type);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "피드백 상세 조회")
    @GetMapping("/feedback/{feedbackId}")
    public ResponseEntity<FeedbackResponse> getFeedback(@CookieValue("memberId") final int memberId,
                                                        @PathVariable("feedbackId") final int feedbackId) {
        final FeedbackResponse response = feedbackService.getFeedback(memberId, feedbackId);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "기간별 일일 피드백 개수 조회")
    @GetMapping("/feedback/daily-count")
    public ResponseEntity<List<DailyFeedbackCountResponse>> getDailyFeedbackCount(@CookieValue("memberId") final int memberId,
                                                                                  @RequestParam("menteeId") final int menteeId,
                                                                                  @RequestParam("startDate") final LocalDate startDate,
                                                                                  @RequestParam("endDate") final LocalDate endDate) {
        final List<DailyFeedbackCountResponse> response =
                feedbackService.getDailyFeedbackCount(memberId, menteeId, startDate, endDate);

        return ResponseEntity.ok(response);
    }
}