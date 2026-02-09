package com.wego.seolstudybe.mentoring.service;

import com.wego.seolstudybe.mentoring.dto.CreateGoalRequest;
import com.wego.seolstudybe.mentoring.dto.GoalResponse;
import com.wego.seolstudybe.mentoring.dto.UpdateGoalRequest;
import com.wego.seolstudybe.mentoring.entity.Goal;
import com.wego.seolstudybe.mentoring.entity.enums.GoalCreator;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface GoalService {
    Goal createGoal(final int memberId, final CreateGoalRequest request, final MultipartFile file);

    void deleteGoal(final int memberId, final int goalId);

    Goal updateGoal(final int memberId, final int goalId, final UpdateGoalRequest request, final MultipartFile file);

    List<GoalResponse> getGoals(final int memberId, final Integer menteeId, final GoalCreator createdBy);
}