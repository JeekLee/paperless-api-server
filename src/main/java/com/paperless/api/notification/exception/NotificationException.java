package com.paperless.api.notification.exception;

import com.paperless.api.core.error.ExceptionInterface;
import com.paperless.api.core.error.httpException.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NotificationException implements ExceptionInterface {
    NOT_FOUND("NOT-001", "Notification not found.", NotFoundException.class);

    private final String errorCode;
    private final String message;
    private final Class<?> aClass;
}
