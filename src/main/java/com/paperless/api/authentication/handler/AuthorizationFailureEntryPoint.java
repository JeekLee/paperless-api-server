package com.paperless.api.authentication.handler;

import com.paperless.api.authentication.exception.AuthorizationException;
import com.paperless.api.core.error.ExceptionCreator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.AuthenticationEntryPoint;

@Slf4j
public class AuthorizationFailureEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, org.springframework.security.core.AuthenticationException authException) {
        throw ExceptionCreator.create(AuthorizationException.ACCESS_TOKEN_NOT_FOUND, "URI: " + request.getRequestURI());
    }
}
