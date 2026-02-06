package com.wego.seolstudybe.mentoring.controller;

import com.wego.seolstudybe.mentoring.dto.CreateGoalRequest;
import com.wego.seolstudybe.mentoring.dto.GoalResponse;
import com.wego.seolstudybe.mentoring.dto.UpdateGoalRequest;
import com.wego.seolstudybe.mentoring.entity.Goal;
import com.wego.seolstudybe.mentoring.entity.enums.GoalCreator;
import com.wego.seolstudybe.mentoring.service.GoalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "목표 - Goal", description = "목표 관련 API")
@RequestMapping("/goals")
@RequiredArgsConstructor
@RestController
public class GoalController {
    private final GoalService goalService;

    @Operation(summary = "목표 등록")
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Integer> createGoal(@CookieValue("memberId") final int memberId,
                                              @Valid @RequestPart(name = "request") final CreateGoalRequest request,
                                              @RequestPart(name = "file", required = false) final MultipartFile file) {
        final Goal goal = goalService.createGoal(memberId, request, file);

        return ResponseEntity.ok(goal.getId());
    }

    @Operation(summary = "목표 수정")
    @PutMapping(value = "/{goalId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Integer> updateGoal(@CookieValue("memberId") final int memberId,
                                              @PathVariable("goalId") final int goalId,
                                              @Valid @RequestPart(name = "request") final UpdateGoalRequest request,
                                              @RequestPart(name = "file", required = false) final MultipartFile file) {
        final Goal goal = goalService.updateGoal(memberId, goalId, request, file);

        return ResponseEntity.ok(goal.getId());
    }

    @Operation(summary = "목표 삭제")
    @DeleteMapping("/{goalId}")
    public ResponseEntity<Void> deleteGoal(@CookieValue("memberId") final int memberId,
                                           @PathVariable("goalId") final int goalId) {
        goalService.deleteGoal(memberId, goalId);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "목표 목록 조회")
    @GetMapping
    public ResponseEntity<List<GoalResponse>> getGoals(@CookieValue("memberId") final int memberId,
                                                   @RequestParam(defaultValue = "ALL") final GoalCreator createdBy) {
        final List<GoalResponse> responses = goalService.getGoals(memberId, createdBy);

        return ResponseEntity.ok(responses);
    }
}