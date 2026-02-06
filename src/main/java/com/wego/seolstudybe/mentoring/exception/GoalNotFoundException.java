package com.wego.seolstudybe.mentoring.exception;

import com.wego.seolstudybe.common.error.ErrorCode;
import com.wego.seolstudybe.common.error.exception.BusinessException;

public class GoalNotFoundException extends BusinessException {
    public GoalNotFoundException() {
        super(ErrorCode.GOAL_NOT_FOUND);
    }
}