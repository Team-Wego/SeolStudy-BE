package com.wego.seolstudybe.mentoring.exception;

import com.wego.seolstudybe.common.error.ErrorCode;
import com.wego.seolstudybe.common.error.exception.BusinessException;

public class FeedbackAlreadyExistException extends BusinessException {
    public FeedbackAlreadyExistException() {
        super(ErrorCode.FEEDBACK_ALREADY_EXIST);
    }
}