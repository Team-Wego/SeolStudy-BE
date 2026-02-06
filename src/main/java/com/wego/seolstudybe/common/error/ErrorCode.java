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
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER001", "회원을 찾을 수 없습니다."),

    /* CHAT */
    CHAT_ROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "CHAT001", "채팅방을 찾을 수 없습니다."),
    CHAT_MESSAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "CHAT002", "메시지를 찾을 수 없습니다."),
    INVALID_CHAT_PARTICIPANT(HttpStatus.FORBIDDEN, "CHAT003", "채팅방 참여 권한이 없습니다."),
    SAME_USER_CHAT_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "CHAT004", "같은 사용자끼리 채팅방을 생성할 수 없습니다."),

    /* FILE */
    FILE_UPLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "FILE001", "파일 업로드에 실패했습니다."),
    FILE_DELETE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "FILE002", "파일 삭제에 실패했습니다."),
    FILE_EMPTY(HttpStatus.BAD_REQUEST, "FILE003", "파일이 비어있습니다."),
    FILE_SIZE_EXCEEDED(HttpStatus.BAD_REQUEST, "FILE004", "파일 크기가 제한을 초과했습니다."),
    INVALID_FILE_TYPE(HttpStatus.BAD_REQUEST, "FILE005", "허용되지 않는 파일 형식입니다."),

    /* NOTIFICATION */
    FCM_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "NOTI001", "FCM 토큰을 찾을 수 없습니다."),
    PUSH_NOTIFICATION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "NOTI002", "푸시 알림 전송에 실패했습니다."),

    /* GOAL */
    GOAL_NOT_FOUND(HttpStatus.NOT_FOUND, "GOAL001", "목표 데이터를 찾을 수 없습니다."),
    GOAL_ACCESS_DENIED(HttpStatus.FORBIDDEN, "GOAL002", "목표 데이터를 수정/삭제 권한이 없습니다."),

    /* FEEDBACK */
    TASK_ID_REQUIRED(HttpStatus.BAD_REQUEST, "FEEDBACK001", "과제 피드백 등록 시 과제 ID가 필수입니다."),

    /* TASK */
    TASK_NOT_FOUND(HttpStatus.NOT_FOUND, "TASK001", "과제가 존재하지 않습니다."),
    NOT_ASSIGNED_MENTEE(HttpStatus.FORBIDDEN, "MEMBER002", "담당 멘티가 아닙니다."),

    /* WORKSHEET */
    WORKSHEET_NOT_FOUND(HttpStatus.NOT_FOUND, "WORKSHEET001", "학습지가 존재하지 않습니다."),
    WORKSHEET_NOT_OWNED(HttpStatus.FORBIDDEN, "WORKSHEET002", "멘티 소유의 학습지가 아닙니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(final HttpStatus status, final String code, final String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}