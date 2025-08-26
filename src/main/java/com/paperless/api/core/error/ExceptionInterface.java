package com.paperless.api.core.error;

public interface ExceptionInterface {
    String getErrorCode();
    String getMessage();
    Class<?> getAClass();
}
