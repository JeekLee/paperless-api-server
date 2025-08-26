package com.paperless.api.authentication.exception;

import com.paperless.api.core.error.ExceptionInterface;
import com.paperless.api.core.error.httpException.InternalServerErrorException;
import com.paperless.api.core.error.httpException.UnauthorizedException;
import lombok.Getter;

@Getter
public enum AuthorizationException implements ExceptionInterface {
    ACCESS_TOKEN_NOT_FOUND("ACT-001", "AccessToken not found", UnauthorizedException.class),
    ACCESS_TOKEN_INVALID("ACT-002", "AccessToken is not valid", UnauthorizedException.class),
    ACCESS_TOKEN_EXPIRED("ACT-003", "AccessToken is expired", UnauthorizedException.class),
    REQOBJ_PARSE_ERROR("ACT-004", "Failed to parse ServerHttpRequest", InternalServerErrorException.class),
    ;

    private final String errorCode;
    private final String message;
    private final Class<?> aClass;

    AuthorizationException(String errorCode, String message, Class<?> aClass) {
        this.errorCode = errorCode;
        this.message = message;
        this.aClass = aClass;
    }
}
