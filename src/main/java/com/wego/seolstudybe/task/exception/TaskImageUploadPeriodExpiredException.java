package com.wego.seolstudybe.task.exception;

import com.wego.seolstudybe.common.error.ErrorCode;
import com.wego.seolstudybe.common.error.exception.BusinessException;

public class TaskImageUploadPeriodExpiredException extends BusinessException {
    public TaskImageUploadPeriodExpiredException() {
        super(ErrorCode.TASK_IMAGE_UPLOAD_PERIOD_EXPIRED);
    }
}
