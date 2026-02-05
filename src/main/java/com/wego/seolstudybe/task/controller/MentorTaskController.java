package com.wego.seolstudybe.task.controller;

import com.wego.seolstudybe.task.dto.CreateTaskRequest;
import com.wego.seolstudybe.task.dto.TaskResponse;
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
            summary = "과제 생성",
            description = "멘토가 담당 멘티에게 과제를 생성합니다."
    )
    @PostMapping("/mentees/{menteeId}/tasks")
    public ResponseEntity<TaskResponse> createTask(
            @PathVariable("menteeId") int menteeId,
            @RequestBody @Valid CreateTaskRequest request
    ) {
        TaskResponse response =
                mentorTaskService.createTask(
                        TEMP_MENTOR_ID, // TODO: 로그인한 사용자 ID로 변경
                        menteeId,
                        request
                );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
