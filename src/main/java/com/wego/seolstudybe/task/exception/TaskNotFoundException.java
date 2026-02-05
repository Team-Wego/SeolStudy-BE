package com.wego.seolstudybe.task.exception;

import com.wego.seolstudybe.common.error.ErrorCode;
import com.wego.seolstudybe.common.error.exception.BusinessException;

public class TaskNotFoundException extends BusinessException {
    public TaskNotFoundException() {
        super(ErrorCode.TASK_NOT_FOUND);
    }
}
