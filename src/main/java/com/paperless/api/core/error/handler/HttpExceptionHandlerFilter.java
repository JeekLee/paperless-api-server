package com.paperless.api.core.error.handler;

import com.paperless.api.core.error.ExceptionResponse;
import com.paperless.api.core.error.httpException.AbstractHttpException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.OutputStream;

@Slf4j
@RequiredArgsConstructor
public class HttpExceptionHandlerFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request, @NonNull HttpServletResponse response, FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        }
        catch (AbstractHttpException e) {
            createdExceptionResponse(response, e);
        }
    }

    private void createdExceptionResponse(HttpServletResponse response, AbstractHttpException e) {
        log.error("[" + e.getErrorCode() + "] : " + e.getErrorLog());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(e.getStatus().value());
        try (OutputStream os = response.getOutputStream()) {
            objectMapper.writeValue(os, ExceptionResponse.builder()
                    .errorCode(e.getErrorCode())
                    .message(e.getMessage())
                    .build()
            );
            os.flush();
        } catch (IOException ioException) {
            throw new RuntimeException("Failed to create HttpExceptionResponse", ioException);
        }
    }
}
