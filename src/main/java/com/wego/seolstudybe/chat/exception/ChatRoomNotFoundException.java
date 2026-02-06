package com.wego.seolstudybe.chat.exception;

import com.wego.seolstudybe.common.error.ErrorCode;
import com.wego.seolstudybe.common.error.exception.BusinessException;

/**
 * 채팅방을 찾을 수 없을 때 발생하는 예외
 */
public class ChatRoomNotFoundException extends BusinessException {

    public ChatRoomNotFoundException() {
        super(ErrorCode.CHAT_ROOM_NOT_FOUND);
    }

    public ChatRoomNotFoundException(final String roomId) {
        super(ErrorCode.CHAT_ROOM_NOT_FOUND, "채팅방을 찾을 수 없습니다: " + roomId);
    }
}
