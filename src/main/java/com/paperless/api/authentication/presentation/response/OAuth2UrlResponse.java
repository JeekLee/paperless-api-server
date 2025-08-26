package com.paperless.api.authentication.presentation.response;

import com.paperless.api.authentication.enums.OAuth2Provider;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OAuth2UrlResponse {
    private String url;
    private String state;
    private OAuth2Provider provider;
    private LocalDateTime expiresAt;

    @Builder
    public OAuth2UrlResponse(String url, String state, OAuth2Provider provider, LocalDateTime expiresAt) {
        this.url = url;
        this.state = state;
        this.provider = provider;
        this.expiresAt = expiresAt;
    }
}