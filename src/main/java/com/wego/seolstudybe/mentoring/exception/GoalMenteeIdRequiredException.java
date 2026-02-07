package com.wego.seolstudybe.mentoring.exception;

import com.wego.seolstudybe.common.error.ErrorCode;
import com.wego.seolstudybe.common.error.exception.BusinessException;

public class GoalMenteeIdRequiredException extends BusinessException {
    public GoalMenteeIdRequiredException() {
        super(ErrorCode.GOAL_MENTEE_ID_REQUIRED);
    }
}