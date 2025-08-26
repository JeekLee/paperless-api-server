package com.paperless.api.authentication.infrastructure.entity;

import com.paperless.api.authentication.domain.model.RefreshToken;
import com.paperless.api.core.utils.EpochConverter;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash("refresh_token")
public class RefreshTokenRedisEntity {
    @Id
    private String memberId;

    private String token;
    private int useCount;
    private long expiresAtEpoch;

    @TimeToLive
    private long ttl;

    @Builder
    public RefreshTokenRedisEntity(String memberId, String token, int useCount, long expiresAtEpoch, long ttl) {
        this.memberId = memberId;
        this.token = token;
        this.useCount = useCount;
        this.expiresAtEpoch = expiresAtEpoch;
        this.ttl = ttl;
    }

    public static RefreshTokenRedisEntity fromDomain(RefreshToken refreshToken) {
        LocalDateTime nowKst = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        long ttlSeconds = ChronoUnit.SECONDS.between(nowKst, refreshToken.getExpiresAt());
        long expiresAtEpoch = refreshToken.getExpiresAt().atZone(ZoneId.of("Asia/Seoul")).toEpochSecond();

        return RefreshTokenRedisEntity.builder()
                .memberId(refreshToken.getMemberId().toString())
                .token(refreshToken.getToken())
                .useCount(refreshToken.getUseCount())
                .expiresAtEpoch(expiresAtEpoch)
                .ttl(Math.max(ttlSeconds, 1))
                .build();
    }

    public RefreshToken toDomain() {
        return RefreshToken.builderWithUseCount()
                .memberId(Long.valueOf(this.memberId))
                .token(this.token)
                .useCount(this.useCount)
                .expiresAt(EpochConverter.convertEpochToDateTime(this.expiresAtEpoch))
                .buildWithUseCount();
    }
}
