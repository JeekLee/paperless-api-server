package com.paperless.api.core.error;

import com.paperless.api.core.error.httpException.AbstractHttpException;
import org.springframework.http.HttpHeaders;

import java.lang.reflect.Constructor;

public class ExceptionCreator {
    @SuppressWarnings("unchecked")
    private static <T> T createInstance(Class<? extends T> clazz, Object... args) {
        Constructor<? extends T>[] constructorArray = (Constructor<? extends T>[]) clazz.getDeclaredConstructors();
        for (Constructor<? extends T> constructor : constructorArray) {
            try {
                return constructor.newInstance(args);
            } catch (Exception ignored) {

            }
        }
        throw new RuntimeException("Failed to create HttpException instance");
    }

    public static AbstractHttpException create(ExceptionInterface e) {
        return (AbstractHttpException) createInstance(e.getAClass(),e.getErrorCode(), e.getMessage());
    }

    public static AbstractHttpException create(ExceptionInterface e, String errorLog) {
        return (AbstractHttpException) createInstance(e.getAClass(), e.getErrorCode(), e.getMessage(), errorLog);
    }

    public static AbstractHttpException create(ExceptionInterface e, String errorLog, HttpHeaders httpHeaders) {
        return (AbstractHttpException) createInstance(e.getAClass(), e.getErrorCode(), e.getMessage(), errorLog, httpHeaders);
    }
}
