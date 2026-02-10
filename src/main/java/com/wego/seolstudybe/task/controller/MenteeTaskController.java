package com.wego.seolstudybe.task.controller;

import com.wego.seolstudybe.task.dto.request.TaskCommentUpdateRequest;
import com.wego.seolstudybe.task.dto.request.TaskCreateRequest;
import com.wego.seolstudybe.task.dto.request.TaskSequenceUpdateRequest;
import com.wego.seolstudybe.task.dto.request.TaskStatusUpdateRequest;
import com.wego.seolstudybe.task.dto.request.TaskUpdateRequest;
import com.wego.seolstudybe.task.dto.response.TaskImageDto;
import com.wego.seolstudybe.task.dto.response.TaskResponse;
import com.wego.seolstudybe.task.service.MenteeTaskService;
import com.wego.seolstudybe.task.service.TaskImageService;
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

@Tag(name = "멘티 할 일 관리 API", description = "멘티 할 일 관리 API")
@RestController
@RequestMapping("/mentees/{menteeId}/tasks")
@RequiredArgsConstructor
public class MenteeTaskController {

    private final MenteeTaskService menteeTaskService;
    private final TaskImageService taskImageService;

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

    @PutMapping("/{taskId}/comment")
    @Operation(summary = "과제 코멘트 등록/수정", description = "멘티가 과제에 코멘트를 등록하거나 수정합니다.")
    public ResponseEntity<TaskResponse> updateTaskComment(
            @PathVariable int menteeId,
            @PathVariable int taskId,
            @Valid @RequestBody TaskCommentUpdateRequest request
    ) {
        TaskResponse response = menteeTaskService.updateTaskComment(menteeId, taskId, request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{taskId}/submit")
    @Operation(summary = "과제 제출", description = "멘티가 과제를 제출합니다. 제출 시점의 시각이 submitted_at에 기록됩니다.")
    public ResponseEntity<TaskResponse> submitTask(
            @PathVariable int menteeId,
            @PathVariable int taskId
    ) {
        TaskResponse response = menteeTaskService.submitTask(menteeId, taskId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{taskId}/images")
    @Operation(summary = "과제 수행 결과 업로드", description = "과제 수행 후 휴대폰 카메라로 촬영하거나 갤러리 이미지 파일을 업로드할 수 있습니다. 과제 날짜 기준 다음날 오전 6시까지만 업로드 가능합니다.")
    public ResponseEntity<List<TaskImageDto>> uploadTaskImages(
            @PathVariable int menteeId,
            @PathVariable int taskId,
            @RequestParam("files") List<MultipartFile> files
    ) {
        List<TaskImageDto> response = taskImageService.uploadTaskImages(menteeId, taskId, files);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
