package com.paperless.api.authentication.domain.model;

import com.paperless.api.authentication.enums.OAuth2Provider;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
public class OAuth2Context {
    private final String state;
    private final OAuth2Provider provider;
    private final String codeVerifier;
    private final String redirectPath;
    private final LocalDateTime expiresAt;

    @Builder
    public OAuth2Context(String state, OAuth2Provider provider, String codeVerifier, String redirectPath) {
        this.state = state;
        this.provider = provider;
        this.codeVerifier = codeVerifier;
        this.redirectPath = redirectPath;
        this.expiresAt = LocalDateTime.now(ZoneId.of("Asia/Seoul")).plusMinutes(5);
    }

    @Builder(builderMethodName = "builderWithExpiresAt", buildMethodName = "buildWithExpiresAt")
    public OAuth2Context(String state, OAuth2Provider provider, String codeVerifier, String redirectPath, LocalDateTime expiresAt) {
        this.state = state;
        this.provider = provider;
        this.codeVerifier = codeVerifier;
        this.redirectPath = redirectPath;
        this.expiresAt = expiresAt;
    }
}
