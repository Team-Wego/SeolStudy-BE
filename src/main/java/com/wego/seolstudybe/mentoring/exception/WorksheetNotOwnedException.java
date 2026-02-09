package com.wego.seolstudybe.mentoring.exception;

import com.wego.seolstudybe.common.error.ErrorCode;
import com.wego.seolstudybe.common.error.exception.BusinessException;

public class WorksheetNotOwnedException extends BusinessException {
    public WorksheetNotOwnedException() {
        super(ErrorCode.WORKSHEET_NOT_OWNED);
    }
}

