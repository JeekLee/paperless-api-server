package com.paperless.api.authentication.domain.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AccessToken {
    private final String token;

    private final Long memberId;
    private final String nickname;
    private final String imagePath;
    private final String email;

    private final LocalDateTime expiresAt;

    @Builder
    public AccessToken(String token, Long memberId, String nickname, String imagePath, String email, LocalDateTime expiresAt) {
        this.token = token;
        this.memberId = memberId;
        this.nickname = nickname;
        this.imagePath = imagePath;
        this.email = email;
        this.expiresAt = expiresAt;
    }
}
