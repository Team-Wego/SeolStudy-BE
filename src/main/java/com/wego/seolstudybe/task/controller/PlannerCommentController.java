package com.wego.seolstudybe.task.controller;

import com.wego.seolstudybe.task.dto.request.PlannerCommentCreateRequest;
import com.wego.seolstudybe.task.dto.request.PlannerCommentUpdateRequest;
import com.wego.seolstudybe.task.dto.response.PlannerCommentResponse;
import com.wego.seolstudybe.task.service.PlannerCommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "플래너 코멘트 API", description = "멘티 플래너 코멘트 관리 API")
@RestController
@RequestMapping("/mentees/{menteeId}/planner-comments")
@RequiredArgsConstructor
public class PlannerCommentController {

    private final PlannerCommentService plannerCommentService;

    @PostMapping
    @Operation(summary = "플래너 코멘트 등록", description = "특정 날짜의 플래너에 코멘트를 등록합니다.")
    public ResponseEntity<PlannerCommentResponse> createPlannerComment(
            @PathVariable("menteeId") int menteeId,
            @Valid @RequestBody PlannerCommentCreateRequest request
    ) {
        PlannerCommentResponse response = plannerCommentService.createPlannerComment(menteeId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{plannerId}")
    @Operation(summary = "플래너 코멘트 수정", description = "플래너 코멘트를 수정합니다.")
    public ResponseEntity<PlannerCommentResponse> updatePlannerComment(
            @PathVariable("menteeId") int menteeId,
            @PathVariable("plannerId") int plannerId,
            @Valid @RequestBody PlannerCommentUpdateRequest request
    ) {
        PlannerCommentResponse response = plannerCommentService.updatePlannerComment(menteeId, plannerId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{plannerId}")
    @Operation(summary = "플래너 코멘트 삭제", description = "플래너 코멘트를 삭제합니다.")
    public ResponseEntity<Void> deletePlannerComment(
            @PathVariable("menteeId") int menteeId,
            @PathVariable("plannerId") int plannerId
    ) {
        plannerCommentService.deletePlannerComment(menteeId, plannerId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{plannerId}/complete")
    @Operation(summary = "플래너 마감 처리", description = "플래너를 마감 처리하고 담당 멘토에게 알림을 전송합니다.")
    public ResponseEntity<PlannerCommentResponse> completePlanner(
            @PathVariable("menteeId") int menteeId,
            @PathVariable("plannerId") int plannerId
    ) {
        PlannerCommentResponse response = plannerCommentService.completePlanner(menteeId, plannerId);
        return ResponseEntity.ok(response);
    }
}
