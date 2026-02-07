package com.wego.seolstudybe.task.service;

import com.wego.seolstudybe.task.dto.request.PlannerCommentCreateRequest;
import com.wego.seolstudybe.task.dto.request.PlannerCommentUpdateRequest;
import com.wego.seolstudybe.task.dto.response.PlannerCommentResponseDTO;

public interface PlannerCommentService {

    PlannerCommentResponseDTO createPlannerComment(int menteeId, PlannerCommentCreateRequest request);

    PlannerCommentResponseDTO updatePlannerComment(int menteeId, int plannerId, PlannerCommentUpdateRequest request);

    void deletePlannerComment(int menteeId, int plannerId);
}
