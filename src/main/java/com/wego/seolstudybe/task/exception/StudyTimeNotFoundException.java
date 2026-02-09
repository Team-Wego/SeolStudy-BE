package com.wego.seolstudybe.task.exception;

import com.wego.seolstudybe.common.error.ErrorCode;
import com.wego.seolstudybe.common.error.exception.BusinessException;

public class StudyTimeNotFoundException extends BusinessException {
    public StudyTimeNotFoundException() {
        super(ErrorCode.STUDY_TIME_NOT_FOUND);
    }
}
