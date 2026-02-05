package com.wego.seolstudybe.mentoring.service;

import com.wego.seolstudybe.mentoring.dto.CreateGoalRequest;
import com.wego.seolstudybe.mentoring.entity.Goal;
import org.springframework.web.multipart.MultipartFile;

public interface GoalService {
    Goal createGoal(final int memberId, final CreateGoalRequest request, final MultipartFile file);

    void deleteGoal(final int memberId, final int goalId);
}