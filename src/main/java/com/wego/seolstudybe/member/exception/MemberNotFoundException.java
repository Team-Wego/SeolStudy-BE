package com.wego.seolstudybe.member.exception;

import com.wego.seolstudybe.common.error.ErrorCode;
import com.wego.seolstudybe.common.error.exception.BusinessException;

public class MemberNotFoundException extends BusinessException {
    public MemberNotFoundException() {
        super(ErrorCode.ENTITY_NOT_FOUND, "존재하지 않는 회원입니다.");
    }
}
