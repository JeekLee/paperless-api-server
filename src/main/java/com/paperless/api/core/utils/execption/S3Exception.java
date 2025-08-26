package com.paperless.api.core.utils.execption;

import com.paperless.api.core.error.ExceptionInterface;
import com.paperless.api.core.error.httpException.BadRequestException;
import com.paperless.api.core.error.httpException.InternalServerErrorException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum S3Exception implements ExceptionInterface {
    INVALID_FILE("AS3-001", "파일 형식이 올바르지 않습니다.", BadRequestException.class),
    UPLOAD_FAILED("AS3-002", "파일 업로드에 실패했습니다.", InternalServerErrorException.class),
    ;

    private final String errorCode;
    private final String message;
    private final Class<?> aClass;
}
