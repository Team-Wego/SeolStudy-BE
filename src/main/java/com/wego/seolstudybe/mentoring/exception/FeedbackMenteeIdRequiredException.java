package com.wego.seolstudybe.mentoring.exception;

import com.wego.seolstudybe.common.error.ErrorCode;
import com.wego.seolstudybe.common.error.exception.BusinessException;

public class FeedbackMenteeIdRequiredException extends BusinessException {
    public FeedbackMenteeIdRequiredException() {
        super(ErrorCode.FEEDBACK_MENTEE_ID_REQUIRED);
    }
}