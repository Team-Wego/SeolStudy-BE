package com.wego.seolstudybe.common.error.exception;

import com.wego.seolstudybe.common.error.ErrorCode;

public class InvalidParameterTypeException extends BusinessException {
    public InvalidParameterTypeException(String parameterName) {
        super(ErrorCode.INVALID_PARAMETER_TYPE,
                String.format("'%s' 파라미터 형식이 올바르지 않습니다.", parameterName));
    }
}
