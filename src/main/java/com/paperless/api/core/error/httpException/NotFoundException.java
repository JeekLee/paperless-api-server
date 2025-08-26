package com.paperless.api.core.error.httpException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class NotFoundException extends AbstractHttpException {
    public NotFoundException(String errorCode, String message) {
        super(NOT_FOUND, errorCode, message);
    }

    public NotFoundException(String errorCode, String message, String errorLog) {
        super(NOT_FOUND, errorCode, message, errorLog);
    }
}
