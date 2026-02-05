package com.wego.seolstudybe.task.service;

import com.wego.seolstudybe.task.dto.CreateTaskRequest;
import com.wego.seolstudybe.task.dto.TaskResponse;

public interface MentorTaskService {
    TaskResponse createTask(int mentorId, int menteeId, CreateTaskRequest request);

}
