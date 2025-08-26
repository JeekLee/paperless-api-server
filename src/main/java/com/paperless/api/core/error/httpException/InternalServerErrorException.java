package com.paperless.api.core.error.httpException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class InternalServerErrorException extends AbstractHttpException {
    public InternalServerErrorException(String errorCode, String message) {
        super(INTERNAL_SERVER_ERROR, errorCode, message);
    }

    public InternalServerErrorException(String errorCode, String message, String errorLog) {
        super(INTERNAL_SERVER_ERROR, errorCode, message, errorLog);
    }
}
