package com.wego.seolstudybe.chat.exception;

import com.wego.seolstudybe.common.error.ErrorCode;
import com.wego.seolstudybe.common.error.exception.BusinessException;

/**
 * 채팅 관련 유효성 검사 예외
 * - 같은 사용자끼리 채팅방 생성 시도
 * - 채팅방 참여 권한 없음
 */
public class InvalidChatException extends BusinessException {

    public InvalidChatException(final ErrorCode errorCode) {
        super(errorCode);
    }

    public InvalidChatException(final ErrorCode errorCode, final String message) {
        super(errorCode, message);
    }
}
