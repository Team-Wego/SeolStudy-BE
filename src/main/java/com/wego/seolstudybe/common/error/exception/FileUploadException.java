package com.wego.seolstudybe.common.error.exception;

import com.wego.seolstudybe.common.error.ErrorCode;

/**
 * 파일 업로드 관련 예외
 */
public class FileUploadException extends BusinessException {

    public FileUploadException() {
        super(ErrorCode.FILE_UPLOAD_FAILED);
    }

    public FileUploadException(final String message) {
        super(ErrorCode.FILE_UPLOAD_FAILED, message);
    }

    public FileUploadException(final ErrorCode errorCode) {
        super(errorCode);
    }

    public FileUploadException(final ErrorCode errorCode, final String message) {
        super(errorCode, message);
    }
}
