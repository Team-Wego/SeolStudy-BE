package com.wego.seolstudybe.mentoring.exception;

import com.wego.seolstudybe.common.error.ErrorCode;
import com.wego.seolstudybe.common.error.exception.BusinessException;

public class WorksheetNotFoundException extends BusinessException {
    public WorksheetNotFoundException() {
        super(ErrorCode.WORKSHEET_NOT_FOUND);
    }
}
