package com.paperless.api.core.validation.annotation;


import com.paperless.api.core.validation.validator.ImageFileValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ImageFileValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidImageFile {

    String message() default "유효하지 않은 이미지 파일입니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String[] allowedTypes() default {"image/jpeg", "image/jpg", "image/png", "image/gif"};

    long maxSizeBytes() default 10 * 1024 * 1024;
}