package com.wego.seolstudybe.task.exception;

import com.wego.seolstudybe.common.error.ErrorCode;
import com.wego.seolstudybe.common.error.exception.BusinessException;

public class PlannerNotFoundException extends BusinessException {
    public PlannerNotFoundException() {
        super(ErrorCode.PLANNER_NOT_FOUND);
    }
}
