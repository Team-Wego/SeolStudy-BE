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
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER001", "회원이 존재하지 않습니다."),
    NOT_ASSIGNED_MENTEE(HttpStatus.FORBIDDEN, "MEMBER002", "담당 멘티가 아닙니다."),

    /* GOAL */
    GOAL_NOT_FOUND(HttpStatus.NOT_FOUND, "GOAL001", "목표가 존재하지 않습니다."),

    /* WORKSHEET */
    WORKSHEET_NOT_FOUND(HttpStatus.NOT_FOUND, "WORKSHEET001", "학습지가 존재하지 않습니다."),
    WORKSHEET_NOT_OWNED(HttpStatus.FORBIDDEN, "WORKSHEET002", "멘티 소유의 학습지가 아닙니다."),

    /* TASK */
    TASK_NOT_FOUND(HttpStatus.NOT_FOUND, "TASK001", "과제가 존재하지 않습니다.");


    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(final HttpStatus status, final String code, final String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}