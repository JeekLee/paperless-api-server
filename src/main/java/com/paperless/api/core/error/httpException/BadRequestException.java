package com.paperless.api.core.error.httpException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class BadRequestException extends AbstractHttpException {
    public BadRequestException(String errorCode, String message) {
        super(BAD_REQUEST, errorCode, message);
    }

    public BadRequestException(String errorCode, String message, String errorLog) {
        super(BAD_REQUEST, errorCode, message, errorLog);
    }
}
