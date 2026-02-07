package com.wego.seolstudybe.mentoring.controller;

import com.wego.seolstudybe.mentoring.dto.MenteeResponse;
import com.wego.seolstudybe.mentoring.dto.MentoringSummaryResponse;
import com.wego.seolstudybe.mentoring.service.MentoringService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "멘토링 - Mentoring", description = "멘토링 관련 API")
@RequiredArgsConstructor
@RestController
public class MentoringController {

    private final MentoringService mentoringService;

    @Operation(summary = "담당 멘티 수, 미작성 과제 피드백 수 조회")
    @GetMapping("/mentors/summary")
    public ResponseEntity<MentoringSummaryResponse> getMentoringSummary(@CookieValue("memberId") final int memberId) {
        final MentoringSummaryResponse summary = mentoringService.getMentoringSummary(memberId);

        return ResponseEntity.ok(summary);
    }

    @Operation(summary = "담당 멘티 목록 조회")
    @GetMapping("/mentors/mentees")
    public ResponseEntity<List<MenteeResponse>> getMenteeList(@CookieValue("memberId") final int memberId) {
        final List<MenteeResponse> menteeList = mentoringService.getMenteeList(memberId);

        return ResponseEntity.ok(menteeList);
    }
}