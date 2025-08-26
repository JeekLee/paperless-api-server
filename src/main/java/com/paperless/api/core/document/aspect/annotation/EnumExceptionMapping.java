package com.paperless.api.core.document.aspect.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumExceptionMapping {
    Class<? extends Enum<?>> enumClass();

    String[] values();
}
