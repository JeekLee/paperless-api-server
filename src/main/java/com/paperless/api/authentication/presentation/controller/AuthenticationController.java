package com.paperless.api.authentication.presentation.controller;

import com.paperless.api.authentication.application.service.AuthenticationService;
import com.paperless.api.authentication.application.service.OAuth2UrlService;
import com.paperless.api.authentication.dto.AuthenticationContext;
import com.paperless.api.authentication.dto.UserPrincipal;
import com.paperless.api.authentication.presentation.api.AuthenticationApi;
import com.paperless.api.authentication.presentation.request.OAuth2LogInRequest;
import com.paperless.api.authentication.presentation.request.OAuth2UrlRequest;
import com.paperless.api.authentication.presentation.response.LoginResponse;
import com.paperless.api.authentication.presentation.response.OAuth2UrlResponse;
import com.paperless.api.authentication.properties.JwtProperties;
import com.paperless.api.core.utils.CookieUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class AuthenticationController implements AuthenticationApi {
    private final JwtProperties jwtProperties;

    private final OAuth2UrlService oAuth2UrlService;
    private final AuthenticationService authenticationService;

    @Override
    public ResponseEntity<OAuth2UrlResponse> oAuth2SignIn(OAuth2UrlRequest request) {
        return ResponseEntity.ok(oAuth2UrlService.generateAuthUrl(
                request.getProvider(), request.getCodeVerifier(), request.getRedirectPath())
        );
    }

    @Override
    public ResponseEntity<LoginResponse> oAuth2LogIn(OAuth2LogInRequest request) {
        AuthenticationContext context = authenticationService.oauth2Login(request.getProvider(), request.getCode(), request.getState());

        ResponseCookie accessTokenCookie = CookieUtil.createCookie(
                jwtProperties.getAccessToken().getTokenKey(),
                context.getAccessToken().getToken(),
                context.getAccessToken().getExpiresAt()
        );
        ResponseCookie refreshTokenCookie = CookieUtil.createCookie(
                jwtProperties.getRefreshToken().getTokenKey(),
                context.getRefreshToken().getToken(),
                context.getRefreshToken().getExpiresAt()
        );

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, accessTokenCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .body(LoginResponse.fromAuthenticationContext(context));
    }

    @Override
    public ResponseEntity<Void> logOut(UserPrincipal userPrincipal) {
        authenticationService.logOut(userPrincipal.getId());

        return ResponseEntity.noContent()
                .header(HttpHeaders.SET_COOKIE, CookieUtil.createEmptyCookie(jwtProperties.getAccessToken().getTokenKey()).toString())
                .header(HttpHeaders.SET_COOKIE, CookieUtil.createEmptyCookie(jwtProperties.getRefreshToken().getTokenKey()).toString())
                .build();
    }

    @Override
    public ResponseEntity<Void> refreshAuthenticationToken(HttpServletRequest request) {
        String token = Optional.ofNullable(request.getCookies())
                .stream()
                .flatMap(Arrays::stream)
                .filter(cookie -> cookie.getName().equals(jwtProperties.getRefreshToken().getTokenKey()))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);

        AuthenticationContext authenticationContext = authenticationService.refreshAuthenticationToken(token);

        ResponseCookie accessTokenCookie = CookieUtil.createCookie(
                jwtProperties.getAccessToken().getTokenKey(),
                authenticationContext.getAccessToken().getToken(),
                authenticationContext.getAccessToken().getExpiresAt()
        );
        ResponseCookie refreshTokenCookie = CookieUtil.createCookie(
                jwtProperties.getRefreshToken().getTokenKey(),
                authenticationContext.getRefreshToken().getToken(),
                authenticationContext.getRefreshToken().getExpiresAt()
        );

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, accessTokenCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .build();
    }
}
