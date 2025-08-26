package com.paperless.api.authentication.exception;

import com.paperless.api.core.error.ExceptionInterface;
import com.paperless.api.core.error.httpException.BadRequestException;
import com.paperless.api.core.error.httpException.InternalServerErrorException;
import com.paperless.api.core.error.httpException.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OAuth2Exception implements ExceptionInterface {
    PROVIDER_NOT_SUPPORTED("OAUTH-001", "OAUTH Provider not supported.", BadRequestException.class),
    FAILED_TO_CREATE_CODE_CHALLENGE("OAUTH-002", "Failed to create code challenge", InternalServerErrorException.class),
    OAUTH2_CONTEXT_NOT_FOUND("OAUTH-003", "OAUTH2 Context not found", NotFoundException.class),
    FAILED_TO_GET_ACCESS_TOKEN("OAUTH-004", "Failed to get token from OAuth2 Provider", InternalServerErrorException.class),
    FAILED_TO_GET_USER_PROFILE("OAUTH-005", "Failed to get user profile from OAuth2 Provider", InternalServerErrorException.class),
    ;

    private final String errorCode;
    private final String message;
    private final Class<?> aClass;
}
