package com.paperless.api.core.document.aspect;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SwaggerExceptionExampleDto {
    private final String errorCode;
    private final String message;

    @Builder
    public SwaggerExceptionExampleDto(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
