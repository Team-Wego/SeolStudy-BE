package com.wego.seolstudybe.member.exception;

import com.wego.seolstudybe.common.error.ErrorCode;
import com.wego.seolstudybe.common.error.exception.BusinessException;

public class NotAssignedMenteeException extends BusinessException {
    public NotAssignedMenteeException() {
        super(ErrorCode.NOT_ASSIGNED_MENTEE);
    }
}

