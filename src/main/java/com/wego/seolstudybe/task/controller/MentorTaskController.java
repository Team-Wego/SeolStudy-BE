package com.wego.seolstudybe.task.controller;

import com.wego.seolstudybe.task.dto.request.CreateTaskRequest;
import com.wego.seolstudybe.task.dto.request.UpdateTaskRequest;
import com.wego.seolstudybe.task.dto.response.CreateTaskResponse;
import com.wego.seolstudybe.task.dto.response.MenteeSubmissionSummaryResponse;
import com.wego.seolstudybe.task.dto.response.PendingFeedbackResponse;
import com.wego.seolstudybe.task.dto.response.UpdateTaskResponse;
import com.wego.seolstudybe.task.service.MentorTaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/mentors")
@RestController
@RequiredArgsConstructor
@Tag(name = "Mentor Task", description = "멘토 과제 관리 API")
public class MentorTaskController {

    private static final int TEMP_MENTOR_ID = 1; // 임시 멘토 계정 (로그인 개발 전 사용)
    private final MentorTaskService mentorTaskService;

    @Operation(
            summary = "과제 등록",
            description = "멘토가 담당 멘티에게 과제를 등록합니다."
    )
    @PostMapping("/mentees/{menteeId}/tasks")
    public ResponseEntity<CreateTaskResponse> createTask(
            @PathVariable("menteeId") int menteeId,
            @RequestBody @Valid CreateTaskRequest request
    ) {
        CreateTaskResponse response =
                mentorTaskService.createTask(
                        TEMP_MENTOR_ID, // TODO: 로그인한 사용자 ID로 변경
                        menteeId,
                        request
                );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "과제 수정",
            description = "멘토가 담당 멘티의 과제를 수정합니다."
    )
    @PutMapping("/tasks/{taskId}")
    public ResponseEntity<UpdateTaskResponse> updateTask(
            @PathVariable("taskId") int taskId,
            @RequestBody @Valid UpdateTaskRequest request
    ) {
        UpdateTaskResponse response =
                mentorTaskService.updateTask(TEMP_MENTOR_ID, taskId, request);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "과제 삭제",
            description = "멘토가 담당 멘티의 과제를 삭제합니다."
    )
    @DeleteMapping("/tasks/{taskId}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable("taskId") int taskId
    ) {
        mentorTaskService.deleteTask(TEMP_MENTOR_ID, taskId);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "멘티별 과제 제출 현황 조회",
            description = "멘토가 담당하는 멘티들의 오늘 과제 제출 현황을 조회합니다."
    )
    @GetMapping("/mentees/submission-summary")
    public ResponseEntity<List<MenteeSubmissionSummaryResponse>> getSubmissionSummary() {
        List<MenteeSubmissionSummaryResponse> response =
                mentorTaskService.getSubmissionSummary(TEMP_MENTOR_ID);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "피드백 미작성 과제 목록 조회",
            description = "피드백이 작성되지 않은 제출된 과제 목록을 조회합니다. 최대 10건, 오래된 순으로 정렬됩니다."
    )
    @GetMapping("/submissions/pending-feedback")
    public ResponseEntity<List<PendingFeedbackResponse>> getPendingFeedbacks() {
        List<PendingFeedbackResponse> response =
                mentorTaskService.getPendingFeedbacks(TEMP_MENTOR_ID);
        return ResponseEntity.ok(response);
    }
}
