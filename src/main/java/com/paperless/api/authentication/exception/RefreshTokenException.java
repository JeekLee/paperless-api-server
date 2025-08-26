package com.paperless.api.authentication.exception;

import com.paperless.api.core.error.ExceptionInterface;
import com.paperless.api.core.error.httpException.BadRequestException;
import com.paperless.api.core.error.httpException.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RefreshTokenException implements ExceptionInterface {
    REFRESH_TOKEN_NOT_FOUND("RFT-001", "RefreshToken not found", NotFoundException.class),
    REFRESH_TOKEN_INVALID("RFT-002", "RefreshToken is not valid", BadRequestException.class),
    REFRESH_TOKEN_EXPIRED("RFT-003", "RefreshToken is not expired", BadRequestException.class),
    REFRESH_TOKEN_NOT_MATCH("RFT-004", "Requested token is not same with stored one.", BadRequestException.class),
    ;

    private final String errorCode;
    private final String message;
    private final Class<?> aClass;
}
