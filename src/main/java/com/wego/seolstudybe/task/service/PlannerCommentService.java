package com.wego.seolstudybe.task.service;

import com.wego.seolstudybe.task.dto.request.PlannerCommentCreateRequest;
import com.wego.seolstudybe.task.dto.request.PlannerCommentUpdateRequest;
import com.wego.seolstudybe.task.dto.response.PlannerCommentResponse;

public interface PlannerCommentService {

    PlannerCommentResponse createPlannerComment(int menteeId, PlannerCommentCreateRequest request);

    PlannerCommentResponse updatePlannerComment(int menteeId, int plannerId, PlannerCommentUpdateRequest request);

    void deletePlannerComment(int menteeId, int plannerId);
}
