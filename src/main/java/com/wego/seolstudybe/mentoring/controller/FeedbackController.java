package com.wego.seolstudybe.mentoring.controller;

import com.wego.seolstudybe.mentoring.dto.CreateFeedbackRequest;
import com.wego.seolstudybe.mentoring.entity.Feedback;
import com.wego.seolstudybe.mentoring.service.FeedbackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
}