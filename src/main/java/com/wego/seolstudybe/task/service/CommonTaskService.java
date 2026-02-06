package com.wego.seolstudybe.task.service;

import com.wego.seolstudybe.task.dto.response.*;

import java.time.LocalDate;
import java.util.List;

public interface CommonTaskService {

    List<TaskListResponse> getTasksByDateRange(int menteeId, LocalDate startDate, LocalDate endDate);

    List<DailyTaskResponse> getDailyTasks(int menteeId, LocalDate date);

    TaskDetailResponse getTaskDetail(int taskId);

    PlannerCommentResponse getPlannerComment(int menteeId, LocalDate date);

    StudyTimeResponse getStudyTime(int menteeId, LocalDate date);
}
