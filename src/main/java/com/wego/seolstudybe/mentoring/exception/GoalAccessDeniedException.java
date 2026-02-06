package com.wego.seolstudybe.mentoring.exception;

import com.wego.seolstudybe.common.error.ErrorCode;
import com.wego.seolstudybe.common.error.exception.BusinessException;

public class GoalAccessDeniedException extends BusinessException {
    public GoalAccessDeniedException() {
        super(ErrorCode.GOAL_ACCESS_DENIED);
    }
}