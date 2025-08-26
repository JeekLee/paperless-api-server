package com.paperless.api.core.web.liveconnect.websocket.interceptor;

import com.paperless.api.authentication.domain.service.JwtResolver;
import com.paperless.api.authentication.dto.UserPrincipal;
import com.paperless.api.authentication.properties.JwtProperties;
import com.paperless.api.core.error.ExceptionCreator;
import com.paperless.api.core.error.httpException.AbstractHttpException;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.paperless.api.authentication.exception.AuthorizationException.REQOBJ_PARSE_ERROR;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtHandshakeInterceptor implements HandshakeInterceptor {
    private final JwtProperties jwtProperties;
    private final JwtResolver jwtResolver;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) {
        try {
            setAttributes(request, attributes);
        } catch (AbstractHttpException e) {
            response.setStatusCode(e.getStatus());
            log.error("[{}] : {}", e.getErrorCode(), e.getErrorLog());
            return false;
        }

        return true;
    }

    private void setAttributes(ServerHttpRequest request, Map<String, Object> attributes) {
        if (!(request instanceof ServletServerHttpRequest)) throw ExceptionCreator.create(REQOBJ_PARSE_ERROR);
        HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();

        String accessToken = Optional.ofNullable(servletRequest.getCookies())
                .stream()
                .flatMap(Arrays::stream)
                .filter(cookie -> cookie.getName().equals(jwtProperties.getAccessToken().getTokenKey()))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);

        UserPrincipal userPrincipal = jwtResolver.getUserPrincipalFromAccessToken(accessToken);
        attributes.put("USER_PRINCIPAL", userPrincipal);
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {
        // HandShake 이후 작업 있다면 추가
    }
}