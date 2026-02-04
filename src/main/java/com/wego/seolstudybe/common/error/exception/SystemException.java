package com.wego.seolstudybe.common.error.exception;

import com.wego.seolstudybe.common.error.ErrorCode;
import lombok.Getter;

@Getter
public class SystemException extends RuntimeException {
    private final ErrorCode errorCode;

    public SystemException(final ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public SystemException(final String message, final ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}