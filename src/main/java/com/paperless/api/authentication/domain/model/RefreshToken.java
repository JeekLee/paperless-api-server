package com.paperless.api.authentication.domain.model;

import com.paperless.api.core.error.ExceptionCreator;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

import static com.paperless.api.authentication.exception.RefreshTokenException.REFRESH_TOKEN_NOT_MATCH;

@Getter
public class RefreshToken {
    private final String token;
    private final Long memberId;
    private final LocalDateTime expiresAt;

    private final int useCount;

    @Builder
    public RefreshToken(String token, Long memberId, LocalDateTime expiresAt) {
        this.token = token;
        this.memberId = memberId;
        this.expiresAt = expiresAt;
        this.useCount = 0;
    }

    @Builder(builderMethodName = "builderWithUseCount", buildMethodName = "buildWithUseCount")
    public RefreshToken(String token, Long memberId, LocalDateTime expiresAt, int useCount) {
        this.token = token;
        this.memberId = memberId;
        this.expiresAt = expiresAt;
        this.useCount = useCount;
    }

    public void validateRefreshRequest(String token) {
        if (!this.token.equals(token)) throw ExceptionCreator.create(REFRESH_TOKEN_NOT_MATCH);
    }

    public RefreshToken increaseUseCount() {
        return RefreshToken.builderWithUseCount()
                .token(token)
                .memberId(memberId)
                .expiresAt(expiresAt)
                .useCount(useCount + 1)
                .buildWithUseCount();
    }
}
