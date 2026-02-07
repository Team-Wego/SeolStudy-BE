package com.wego.seolstudybe.task.controller;

import com.wego.seolstudybe.mentoring.entity.enums.Subject;
import com.wego.seolstudybe.task.dto.response.StudyStatusResponse;
import com.wego.seolstudybe.task.service.CommonTaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Tag(name = "멘티 학습 현황 API", description = "멘티 마이페이지 학습 현황 조회 API")
@RestController
@RequestMapping("/mentees/{menteeId}")
@RequiredArgsConstructor
public class MenteeStudyStatusController {

    private final CommonTaskService commonTaskService;

    @GetMapping("/study-status")
    @Operation(summary = "기간/과목별 학습 현황 조회", description = "기간 내 과목별 과제 달성률을 조회합니다. 과목이 null이면 전체 과제 달성률과 과목별 달성률을 함께 반환합니다.")
    public ResponseEntity<StudyStatusResponse> getStudyStatus(
            @PathVariable int menteeId,
            @Parameter(description = "조회 시작일 (yyyy-MM-dd)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @Parameter(description = "조회 종료일 (yyyy-MM-dd)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @Parameter(description = "과목 (KOR, ENG, MATH). null이면 전체 과목 조회")
            @RequestParam(required = false) Subject subject
    ) {
        StudyStatusResponse studyStatus = commonTaskService.getStudyStatus(menteeId, startDate, endDate, subject);
        return ResponseEntity.ok(studyStatus);
    }
}
