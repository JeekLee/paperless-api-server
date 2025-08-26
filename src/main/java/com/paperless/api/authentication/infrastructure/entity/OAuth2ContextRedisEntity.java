package com.paperless.api.authentication.infrastructure.entity;

import com.paperless.api.authentication.domain.model.OAuth2Context;
import com.paperless.api.authentication.enums.OAuth2Provider;
import com.paperless.api.core.utils.EpochConverter;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash("oauth2_context")
@Slf4j
public class OAuth2ContextRedisEntity {
    @Id
    private String state;
    private String provider;
    private String codeVerifier;
    private String redirectPath;
    private long expiresAtEpoch;

    @TimeToLive
    private long ttl;

    @Builder
    public OAuth2ContextRedisEntity(String state, String provider, String codeVerifier, String redirectPath, long expiresAtEpoch, long ttl) {
        this.state = state;
        this.provider = provider;
        this.codeVerifier = codeVerifier;
        this.redirectPath = redirectPath;
        this.expiresAtEpoch = expiresAtEpoch;
        this.ttl = ttl;
    }

    public static OAuth2ContextRedisEntity fromDomain(OAuth2Context authInfo) {
        LocalDateTime nowKst = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        long ttlSeconds = ChronoUnit.SECONDS.between(nowKst, authInfo.getExpiresAt());

        long expiresAtEpoch = authInfo.getExpiresAt().atZone(ZoneId.of("Asia/Seoul")).toEpochSecond();

        return OAuth2ContextRedisEntity.builder()
                .state(authInfo.getState())
                .provider(authInfo.getProvider().name())
                .codeVerifier(authInfo.getCodeVerifier())
                .redirectPath(authInfo.getRedirectPath())
                .expiresAtEpoch(expiresAtEpoch)
                .ttl(Math.max(ttlSeconds, 1))
                .build();
    }

    public OAuth2Context toDomain() {
        return OAuth2Context.builderWithExpiresAt()
                .state(this.state)
                .provider(OAuth2Provider.valueOf(this.provider))
                .codeVerifier(this.codeVerifier)
                .redirectPath(this.redirectPath)
                .expiresAt(EpochConverter.convertEpochToDateTime(this.expiresAtEpoch))
                .buildWithExpiresAt();
    }
}
