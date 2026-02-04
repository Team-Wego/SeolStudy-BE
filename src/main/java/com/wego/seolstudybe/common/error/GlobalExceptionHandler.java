package com.wego.seolstudybe.common.error;

import com.wego.seolstudybe.common.error.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
     * - MaterialNotFoundException, MaterialCodeDuplicatedException 등
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
        log.error("[BusinessException] code={}, message={}", e.getErrorCode().getCode(), e.getMessage());
        final ErrorCode errorCode = e.getErrorCode();
        final ErrorResponse errorResponse = ErrorResponse.of(errorCode.getCode(), errorCode.getMessage());
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