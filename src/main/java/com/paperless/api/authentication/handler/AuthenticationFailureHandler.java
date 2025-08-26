package com.paperless.api.authentication.handler;

import com.paperless.api.authentication.dto.UserPrincipal;
import com.paperless.api.authentication.exception.AuthenticationException;
import com.paperless.api.core.error.ExceptionCreator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

@Slf4j
public class AuthenticationFailureHandler implements AccessDeniedHandler {
    @Override
    public void handle(
            HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException
    ) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        UserPrincipal principal = authentication.getPrincipal() instanceof UserPrincipal ? (UserPrincipal) authentication.getPrincipal() : null;
        String nickname = principal != null ? principal.getNickname() : "unknown";

        throw ExceptionCreator.create(
                AuthenticationException.PERMISSION_REQUIRED,
                "Member [" + nickname + "] attempted to access protected URL " + request.getRequestURI()
        );
    }
}
