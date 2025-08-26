package com.paperless.api.core.error;

import com.paperless.api.core.error.httpException.AbstractHttpException;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Getter
@Builder
public class ExceptionResponse {
    private final String errorCode;
    private final String message;
    private final String timeStamp = LocalDateTime.now().toString();

    public static ResponseEntity<ExceptionResponse> toResponseEntity(AbstractHttpException e) {
        return ResponseEntity
                .status(e.getStatus())
                .headers(e.getHttpHeaders())
                .body(
                        ExceptionResponse.builder()
                                .errorCode(e.getErrorCode())
                                .message(e.getMessage())
                                .build()
                );
    }

    public static ResponseEntity<ExceptionResponse> toResponseEntity(HttpStatus httpStatus, String errorCode, String message) {
        return ResponseEntity
                .status(httpStatus)
                .body(
                        ExceptionResponse.builder()
                                .errorCode(errorCode)
                                .message(message)
                                .build()
                );
    }
}
