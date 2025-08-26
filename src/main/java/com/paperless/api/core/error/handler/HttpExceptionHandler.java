package com.paperless.api.core.error.handler;

import com.paperless.api.core.error.ExceptionResponse;
import com.paperless.api.core.error.httpException.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.springframework.validation.FieldError;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class HttpExceptionHandler {
    private ResponseEntity<?> createExceptionResponse(AbstractHttpException e) {
        log.error("[" + e.getErrorCode() + "] : " + e.getErrorLog());
        return ExceptionResponse.toResponseEntity(e);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleBadRequestException(BadRequestException e) {
        return createExceptionResponse(e);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<?> handleForbiddenException(ForbiddenException e) {
        return createExceptionResponse(e);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(NotFoundException e) {
        return createExceptionResponse(e);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<?> handleUnauthorizedException(UnauthorizedException e) {
        return createExceptionResponse(e);
    }

    @ExceptionHandler(ServiceUnavailableException.class)
    public ResponseEntity<?> handleServiceUnavailableException(ServiceUnavailableException e) {
        return createExceptionResponse(e);
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<?> handleInternalServerErrorException(InternalServerErrorException e) {
        return createExceptionResponse(e);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String errorMessage = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));

        return ExceptionResponse.toResponseEntity(HttpStatus.BAD_REQUEST, "REQ-001", errorMessage);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return ExceptionResponse.toResponseEntity(HttpStatus.BAD_REQUEST, "REQ-002", e.getMessage());
    }
}
