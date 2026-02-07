package com.wego.seolstudybe.mentoring.exception;

import com.wego.seolstudybe.common.error.ErrorCode;
import com.wego.seolstudybe.common.error.exception.BusinessException;

public class FeedbackAccessDeniedException extends BusinessException {
    public FeedbackAccessDeniedException() {
        super(ErrorCode.FEEDBACK_ACCESS_DENIED);
    }
}