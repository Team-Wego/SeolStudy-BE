package com.wego.seolstudybe.task.controller;

import com.wego.seolstudybe.task.dto.response.*;
import com.wego.seolstudybe.task.service.CommonTaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "공통 과제/할일 조회 API", description = "멘토/멘티 공통으로 사용하는 과제/할일 조회 API")
@RestController
@RequiredArgsConstructor
public class CommonTaskController {

    private final CommonTaskService commonTaskService;

    @GetMapping("/mentees/{menteeId}/tasks")
    @Operation(summary = "기간별 과제/할일 목록 조회", description = "특정 기간 내의 과제 및 할일 목록을 조회합니다.")
    public ResponseEntity<List<TaskListResponse>> getTasksByDateRange(
            @PathVariable int menteeId,
            @Parameter(description = "조회 시작일 (yyyy-MM-dd)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @Parameter(description = "조회 종료일 (yyyy-MM-dd)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        List<TaskListResponse> tasks = commonTaskService.getTasksByDateRange(menteeId, startDate, endDate);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/mentees/{menteeId}/planner/tasks")
    @Operation(summary = "일일 할일/과제 목록 조회", description = "특정 날짜의 할일 및 과제 목록을 공부 시간과 함께 조회합니다.")
    public ResponseEntity<List<DailyTaskResponse>> getDailyTasks(
            @PathVariable int menteeId,
            @Parameter(description = "조회 날짜 (yyyy-MM-dd)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        List<DailyTaskResponse> tasks = commonTaskService.getDailyTasks(menteeId, date);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/tasks/{taskId}")
    @Operation(summary = "과제/할일 상세 조회", description = "과제 또는 할일의 상세 정보를 조회합니다.")
    public ResponseEntity<TaskDetailResponse> getTaskDetail(
            @PathVariable int taskId
    ) {
        TaskDetailResponse task = commonTaskService.getTaskDetail(taskId);
        return ResponseEntity.ok(task);
    }

    @GetMapping("/mentees/{menteeId}/planner/comments")
    @Operation(summary = "플래너 코멘트 조회", description = "특정 날짜의 플래너 코멘트를 조회합니다.")
    public ResponseEntity<PlannerCommentResponse> getPlannerComment(
            @PathVariable int menteeId,
            @Parameter(description = "조회 날짜 (yyyy-MM-dd)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        PlannerCommentResponse comment = commonTaskService.getPlannerComment(menteeId, date);
        return ResponseEntity.ok(comment);
    }

    @GetMapping("/mentees/{menteeId}/planner/study-time")
    @Operation(summary = "공부 시간 조회", description = "특정 날짜의 총 공부 시간을 조회합니다.")
    public ResponseEntity<DailyStudyTimeResponse> getStudyTime(
            @PathVariable int menteeId,
            @Parameter(description = "조회 날짜 (yyyy-MM-dd)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        DailyStudyTimeResponse studyTime = commonTaskService.getStudyTime(menteeId, date);
        return ResponseEntity.ok(studyTime);
    }
}
