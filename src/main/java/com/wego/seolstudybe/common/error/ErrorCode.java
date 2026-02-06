package com.wego.seolstudybe.common.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    /* COMMON */
    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "COMMON001", "요청한 데이터를 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON002", "서버 내부 오류가 발생했습니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON003", "잘못된 요청입니다."),

    /* MEMBER */
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER001", "회원 데이터를 찾을 수 없습니다."),

    /* GOAL */
    GOAL_NOT_FOUND(HttpStatus.NOT_FOUND, "GOAL001", "목표 데이터를 찾을 수 없습니다."),
    GOAL_ACCESS_DENIED(HttpStatus.FORBIDDEN, "GOAL002", "목표 데이터를 수정/삭제 권한이 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(final HttpStatus status, final String code, final String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}