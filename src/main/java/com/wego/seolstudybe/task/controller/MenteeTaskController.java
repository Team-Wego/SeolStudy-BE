package com.wego.seolstudybe.task.controller;

import com.wego.seolstudybe.task.dto.request.TaskCreateRequest;
import com.wego.seolstudybe.task.dto.request.TaskSequenceUpdateRequest;
import com.wego.seolstudybe.task.dto.request.TaskStatusUpdateRequest;
import com.wego.seolstudybe.task.dto.request.TaskUpdateRequest;
import com.wego.seolstudybe.task.dto.response.TaskResponse;
import com.wego.seolstudybe.task.service.MenteeTaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "멘티 할 일 관리 API", description = "멘티 할 일 관리 API")
@RestController
@RequestMapping("/mentees/{menteeId}/tasks")
@RequiredArgsConstructor
public class MenteeTaskController {

    private final MenteeTaskService menteeTaskService;

    @PostMapping
    @Operation(summary = "할 일 등록", description = "멘티의 할 일을 등록합니다.")
    public ResponseEntity<TaskResponse> createTask(
            @PathVariable int menteeId,
            @Valid @RequestBody TaskCreateRequest request
    ) {
        TaskResponse response = menteeTaskService.createTask(menteeId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{taskId}")
    @Operation(summary = "할 일 내용 수정", description = "멘티의 할 일 내용을 수정합니다.")
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable int menteeId,
            @PathVariable int taskId,
            @Valid @RequestBody TaskUpdateRequest request
    ) {
        TaskResponse response = menteeTaskService.updateTask(menteeId, taskId, request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{taskId}/sequence")
    @Operation(summary = "할 일 순서 변경", description = "멘티의 할 일 순서를 변경합니다.")
    public ResponseEntity<Void> updateTaskSequence(
            @PathVariable int menteeId,
            @PathVariable int taskId,
            @Valid @RequestBody TaskSequenceUpdateRequest request
    ) {
        menteeTaskService.updateTaskSequence(menteeId, taskId, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{taskId}")
    @Operation(summary = "할 일 삭제", description = "멘티의 할 일을 삭제합니다.")
    public ResponseEntity<Void> deleteTask(
            @PathVariable int menteeId,
            @PathVariable int taskId
    ) {
        menteeTaskService.deleteTask(menteeId, taskId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{taskId}/status")
    @Operation(summary = "할 일 상태 변경", description = "멘티의 할 일 체크 상태를 변경합니다.")
    public ResponseEntity<TaskResponse> updateTaskStatus(
            @PathVariable int menteeId,
            @PathVariable int taskId,
            @Valid @RequestBody TaskStatusUpdateRequest request
    ) {
        TaskResponse response = menteeTaskService.updateTaskStatus(menteeId, taskId, request);
        return ResponseEntity.ok(response);
    }
}
