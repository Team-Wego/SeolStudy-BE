package com.wego.seolstudybe.task.service;

import com.wego.seolstudybe.task.dto.CreateTaskRequest;
import com.wego.seolstudybe.task.dto.CreateTaskResponse;
import com.wego.seolstudybe.task.dto.TaskResponse;
import com.wego.seolstudybe.task.dto.UpdateTaskRequest;

public interface MentorTaskService {
    CreateTaskResponse createTask(int mentorId, int menteeId, CreateTaskRequest request);

    TaskResponse updateTask(int mentorId, int taskId, UpdateTaskRequest request);
}
