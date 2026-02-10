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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("/mentors")
@RestController
@RequiredArgsConstructor
@Tag(name = "Mentor Task", description = "멘토 과제 관리 API")
public class MentorTaskController {

    private final MentorTaskService mentorTaskService;

    @Operation(
            summary = "과제 등록",
            description = "멘토가 담당 멘티에게 과제를 등록합니다."
    )
    @PostMapping(value = "/mentees/{menteeId}/tasks", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CreateTaskResponse> createTask(
            @CookieValue(value = "memberId", defaultValue = "1") int memberId,
            @PathVariable("menteeId") int menteeId,
            @Valid @RequestPart("request") CreateTaskRequest request,
            @RequestPart(name = "files", required = false) List<MultipartFile> files
    ) {
        CreateTaskResponse response = mentorTaskService.createTask(memberId, menteeId, request, files);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "과제 수정",
            description = "멘토가 담당 멘티의 과제를 수정합니다."
    )
    @PutMapping("/tasks/{taskId}")
    public ResponseEntity<UpdateTaskResponse> updateTask(
            @CookieValue(value = "memberId", defaultValue = "1") int memberId,
            @PathVariable("taskId") int taskId,
            @RequestBody @Valid UpdateTaskRequest request
    ) {
        UpdateTaskResponse response = mentorTaskService.updateTask(memberId, taskId, request);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "과제 삭제",
            description = "멘토가 담당 멘티의 과제를 삭제합니다."
    )
    @DeleteMapping("/tasks/{taskId}")
    public ResponseEntity<Void> deleteTask(
            @CookieValue(value = "memberId", defaultValue = "1") int memberId,
            @PathVariable("taskId") int taskId
    ) {
        mentorTaskService.deleteTask(memberId, taskId);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "멘티별 과제 제출 현황 조회",
            description = "멘토가 담당하는 멘티들의 오늘 과제 제출 현황을 조회합니다."
    )
    @GetMapping("/mentees/submission-summary")
    public ResponseEntity<List<MenteeSubmissionSummaryResponse>> getSubmissionSummary(
            @CookieValue(value = "memberId", defaultValue = "1") int memberId
    ) {
        List<MenteeSubmissionSummaryResponse> response = mentorTaskService.getSubmissionSummary(memberId);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "피드백 미작성 과제 목록 조회",
            description = "피드백이 작성되지 않은 제출된 과제 목록을 조회합니다. 최대 10건, 오래된 순으로 정렬됩니다."
    )
    @GetMapping("/submissions/pending-feedback")
    public ResponseEntity<List<PendingFeedbackResponse>> getPendingFeedbacks(
            @CookieValue(value = "memberId", defaultValue = "1") int memberId
    ) {
        List<PendingFeedbackResponse> response = mentorTaskService.getPendingFeedbacks(memberId);
        return ResponseEntity.ok(response);
    }
}
