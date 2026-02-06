package com.wego.seolstudybe.member.exception;

import com.wego.seolstudybe.common.error.ErrorCode;
import com.wego.seolstudybe.common.error.exception.BusinessException;

public class InvalidPasswordException extends BusinessException {
    public InvalidPasswordException() {
        super(ErrorCode.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
    }
}
