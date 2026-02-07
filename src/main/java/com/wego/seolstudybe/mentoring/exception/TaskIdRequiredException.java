package com.wego.seolstudybe.mentoring.exception;

import com.wego.seolstudybe.common.error.ErrorCode;
import com.wego.seolstudybe.common.error.exception.BusinessException;

public class TaskIdRequiredException extends BusinessException {
    public TaskIdRequiredException() {
        super(ErrorCode.TASK_ID_REQUIRED);
    }
}