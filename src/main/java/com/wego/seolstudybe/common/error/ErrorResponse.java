package com.wego.seolstudybe.common.error;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@Getter
public class ErrorResponse {

    private String code;

    private String message;

    private String timestamp;

    public ErrorResponse(final String code, final String message) {
        this.code = code;
        this.message = message;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public static ErrorResponse of(final String code, final String message) {
        return new ErrorResponse(code, message);
    }
}