package com.wego.seolstudybe.task.service;

import com.wego.seolstudybe.mentoring.entity.enums.Subject;
import com.wego.seolstudybe.task.dto.response.*;

import java.time.LocalDate;
import java.util.List;

public interface CommonTaskService {

    List<TaskListResponse> getTasksByDateRange(int menteeId, LocalDate startDate, LocalDate endDate);

    List<DailyTaskResponse> getDailyTasks(int menteeId, LocalDate date);

    TaskDetailResponse getTaskDetail(int taskId);

    GetPlannerCommentDto getPlannerComment(int menteeId, LocalDate date);

    DailyStudyTimeResponse getStudyTime(int menteeId, LocalDate date);

    StudyStatusResponse getStudyStatus(int menteeId, LocalDate startDate, LocalDate endDate, Subject subject);
}
