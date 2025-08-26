package com.paperless.api.member.exception;

import com.paperless.api.core.error.ExceptionInterface;
import com.paperless.api.core.error.httpException.BadRequestException;
import com.paperless.api.core.error.httpException.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberException implements ExceptionInterface {
    MEMBER_NOT_FOUND("MEMBER-001", "Member not found", NotFoundException.class),
    NICKNAME_ALREADY_EXISTS("MEMBER-002", "Nickname already exists", BadRequestException.class)
    ;

    private final String errorCode;
    private final String message;
    private final Class<?> aClass;
}
