package com.wego.seolstudybe.task.service;

import com.wego.seolstudybe.task.dto.request.TaskCommentUpdateRequest;
import com.wego.seolstudybe.task.dto.request.TaskCreateRequest;
import com.wego.seolstudybe.task.dto.request.TaskSequenceUpdateRequest;
import com.wego.seolstudybe.task.dto.request.TaskStatusUpdateRequest;
import com.wego.seolstudybe.task.dto.request.TaskUpdateRequest;
import com.wego.seolstudybe.task.dto.response.TaskResponse;

public interface MenteeTaskService {

    TaskResponse createTask(int memberId, TaskCreateRequest request);

    TaskResponse updateTask(int memberId, int taskId, TaskUpdateRequest request);

    void updateTaskSequence(int memberId, int taskId, TaskSequenceUpdateRequest request);

    void deleteTask(int memberId, int taskId);

    TaskResponse updateTaskStatus(int memberId, int taskId, TaskStatusUpdateRequest request);

    TaskResponse updateTaskComment(int memberId, int taskId, TaskCommentUpdateRequest request);
}
