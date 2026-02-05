package com.wego.seolstudybe.mentoring.exception;

import com.wego.seolstudybe.common.error.ErrorCode;
import com.wego.seolstudybe.common.error.exception.BusinessException;

public class GoalNameDuplicatedException extends BusinessException {
    public GoalNameDuplicatedException() {
        super(ErrorCode.GOAL_NAME_DUPLICATED);
    }
}