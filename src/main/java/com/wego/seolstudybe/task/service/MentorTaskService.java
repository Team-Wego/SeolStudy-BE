package com.wego.seolstudybe.task.service;

import com.wego.seolstudybe.task.dto.request.CreateTaskRequest;
import com.wego.seolstudybe.task.dto.request.UpdateTaskRequest;
import com.wego.seolstudybe.task.dto.response.CreateTaskResponse;
import com.wego.seolstudybe.task.dto.response.MenteeSubmissionSummaryResponse;
import com.wego.seolstudybe.task.dto.response.PendingFeedbackResponse;
import com.wego.seolstudybe.task.dto.response.UpdateTaskResponse;

import java.util.List;

public interface MentorTaskService {
    CreateTaskResponse createTask(int mentorId, int menteeId, CreateTaskRequest request);

    UpdateTaskResponse updateTask(int mentorId, int taskId, UpdateTaskRequest request);

    void deleteTask(int mentorId, int taskId);

    List<MenteeSubmissionSummaryResponse> getSubmissionSummary(int mentorId);

    List<PendingFeedbackResponse> getPendingFeedbacks(int mentorId);
}
