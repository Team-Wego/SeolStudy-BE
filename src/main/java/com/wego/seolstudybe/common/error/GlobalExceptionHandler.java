package com.wego.seolstudybe.common.error;

import com.wego.seolstudybe.common.error.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

/**
 * 전역 예외 처리 핸들러
 * - 모든 예외를 한국어 에러 메시지로 변환하여 응답합니다.
 * - 에러 응답 형식: {code, message, timestamp}
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 비즈니스 예외 처리
     * - 채팅방 없음, 파일 업로드 실패 등
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
        log.error("[BusinessException] code={}, message={}", e.getErrorCode().getCode(), e.getMessage());
        final ErrorCode errorCode = e.getErrorCode();
        final ErrorResponse errorResponse = ErrorResponse.of(errorCode.getCode(), e.getMessage());
        return new ResponseEntity<>(errorResponse, errorCode.getStatus());
    }

    /**
     * 유효성 검사 실패 예외 처리
     * - @Valid 어노테이션으로 인한 검증 실패 시 발생
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("[ValidationException] {}", e.getMessage());
        final ErrorCode errorCode = ErrorCode.BAD_REQUEST;
        final String message = e.getBindingResult().getFieldErrors().isEmpty()
                ? errorCode.getMessage()
                : e.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        final ErrorResponse errorResponse = ErrorResponse.of(errorCode.getCode(), message);
        return new ResponseEntity<>(errorResponse, errorCode.getStatus());
    }

    /**
     * IllegalArgumentException 처리
     * - 잘못된 인자 전달 시 발생
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("[IllegalArgumentException] {}", e.getMessage());
        final ErrorCode errorCode = ErrorCode.BAD_REQUEST;
        final ErrorResponse errorResponse = ErrorResponse.of(errorCode.getCode(), e.getMessage());
        return new ResponseEntity<>(errorResponse, errorCode.getStatus());
    }

    /**
     * 파일 크기 초과 예외 처리
     * - Spring의 기본 파일 크기 제한을 초과할 때 발생
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorResponse> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        log.error("[MaxUploadSizeExceededException] {}", e.getMessage());
        final ErrorCode errorCode = ErrorCode.FILE_SIZE_EXCEEDED;
        final ErrorResponse errorResponse = ErrorResponse.of(errorCode.getCode(), errorCode.getMessage());
        return new ResponseEntity<>(errorResponse, errorCode.getStatus());
    }

    /**
     * 파일 파라미터 누락 예외 처리
     * - 필수 파일 파라미터가 없을 때 발생
     */
    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<ErrorResponse> handleMissingServletRequestPartException(MissingServletRequestPartException e) {
        log.error("[MissingServletRequestPartException] {}", e.getMessage());
        final ErrorCode errorCode = ErrorCode.FILE_EMPTY;
        final ErrorResponse errorResponse = ErrorResponse.of(errorCode.getCode(), "파일을 선택해주세요.");
        return new ResponseEntity<>(errorResponse, errorCode.getStatus());
    }

    /**
     * 필수 파라미터 누락 예외 처리
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.error("[MissingServletRequestParameterException] {}", e.getMessage());
        final ErrorCode errorCode = ErrorCode.BAD_REQUEST;
        final ErrorResponse errorResponse = ErrorResponse.of(errorCode.getCode(),
                String.format("필수 파라미터가 누락되었습니다: %s", e.getParameterName()));
        return new ResponseEntity<>(errorResponse, errorCode.getStatus());
    }

    /**
     * 기타 모든 예외 처리
     * - 예상하지 못한 서버 오류
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("[UnhandledException] {}", e.getMessage(), e);
        final ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
        final ErrorResponse errorResponse = ErrorResponse.of(errorCode.getCode(), errorCode.getMessage());
        return new ResponseEntity<>(errorResponse, errorCode.getStatus());
    }
}