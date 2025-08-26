package com.paperless.api.core.error.httpException;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

public class UnauthorizedException extends AbstractHttpException {
    public UnauthorizedException(String errorCode, String message) {
        super(UNAUTHORIZED, errorCode, message);
    }

    public UnauthorizedException(String errorCode, String message, String errorLog) {
        super(UNAUTHORIZED, errorCode, message, errorLog);
    }
}
