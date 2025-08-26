package com.paperless.api.core.error.httpException;

import static org.springframework.http.HttpStatus.FORBIDDEN;

public class ForbiddenException extends AbstractHttpException {
    public ForbiddenException(String errorCode, String message) {
        super(FORBIDDEN, errorCode, message);
    }

    public ForbiddenException(String errorCode, String message, String errorLog) {
        super(FORBIDDEN, errorCode, message, errorLog);
    }
}
