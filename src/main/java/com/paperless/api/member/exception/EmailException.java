package com.paperless.api.member.exception;

import com.paperless.api.core.error.ExceptionInterface;
import com.paperless.api.core.error.httpException.BadRequestException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EmailException implements ExceptionInterface {
    INVALID_EMAIL_FORMAT("EMAIL-001", "Invalid email format.", BadRequestException.class),
    TOO_LONG_EMAIL("EMAIL-001", "Email is longer than 320 letters.(RFC 5321 Standard)", BadRequestException.class),
    ;

    private final String errorCode;
    private final String message;
    private final Class<?> aClass;
}
