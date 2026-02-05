package com.wego.seolstudybe.task.controller;

import com.wego.seolstudybe.task.dto.CreateTaskRequest;
import com.wego.seolstudybe.task.dto.CreateTaskResponse;
import com.wego.seolstudybe.task.dto.TaskResponse;
import com.wego.seolstudybe.task.dto.UpdateTaskRequest;
import com.wego.seolstudybe.task.service.MentorTaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable("taskId") int taskId,
            @RequestBody @Valid UpdateTaskRequest request
    ) {
        TaskResponse response =
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
}
