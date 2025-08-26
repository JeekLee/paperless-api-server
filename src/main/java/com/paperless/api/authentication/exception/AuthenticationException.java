package com.paperless.api.authentication.exception;

import com.paperless.api.core.error.ExceptionInterface;
import com.paperless.api.core.error.httpException.ForbiddenException;
import lombok.Getter;

@Getter
public enum AuthenticationException implements ExceptionInterface {
    PERMISSION_REQUIRED("ATH-001", "Permission required", ForbiddenException.class);

    private final String errorCode;
    private final String message;
    private final Class<?> aClass;

    AuthenticationException(String errorCode, String message, Class<?> aClass) {
        this.errorCode = errorCode;
        this.message = message;
        this.aClass = aClass;
    }
}
