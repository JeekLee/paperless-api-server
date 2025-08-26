package com.paperless.api.core.error.httpException;

import lombok.Getter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

@Getter
public abstract class AbstractHttpException extends RuntimeException {
    private final HttpStatus status;
    private final String errorCode;
    private final String message;
    private final String errorLog;
    private final HttpHeaders httpHeaders;

    public AbstractHttpException(HttpStatus status, String errorCode, String message) {
        super(message);
        this.status = status;
        this.errorCode = errorCode;
        this.message = message;
        this.errorLog = null;
        this.httpHeaders = HttpHeaders.EMPTY;
    }

    public AbstractHttpException(HttpStatus status, String errorCode, String message, String errorLog) {
        super(message);
        this.status = status;
        this.errorCode = errorCode;
        this.message = message;
        this.errorLog = errorLog;
        this.httpHeaders = HttpHeaders.EMPTY;
    }

    public AbstractHttpException(HttpStatus status, String errorCode, String message, String errorLog, HttpHeaders httpHeaders) {
        super(message);
        this.status = status;
        this.errorCode = errorCode;
        this.message = message;
        this.errorLog = errorLog;
        this.httpHeaders = httpHeaders;
    }
}
