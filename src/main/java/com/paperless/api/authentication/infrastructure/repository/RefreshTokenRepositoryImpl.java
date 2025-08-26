package com.paperless.api.authentication.infrastructure.repository;

import com.paperless.api.authentication.application.repository.RefreshTokenRepository;
import com.paperless.api.authentication.domain.model.RefreshToken;
import com.paperless.api.authentication.infrastructure.entity.RefreshTokenRedisEntity;
import com.paperless.api.authentication.infrastructure.repository.redis.RedisRefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {
    private final RedisRefreshTokenRepository redisRefreshTokenRepository;

    @Override
    public RefreshToken save(RefreshToken refreshToken) {
        return redisRefreshTokenRepository.save(RefreshTokenRedisEntity.fromDomain(refreshToken)).toDomain();
    }

    @Override
    public void deleteByMemberId(Long memberId) {
        redisRefreshTokenRepository.deleteById(memberId.toString());
    }

    @Override
    public Optional<RefreshToken> findByMemberId(Long memberId) {
        return redisRefreshTokenRepository.findById(memberId.toString()).map(RefreshTokenRedisEntity::toDomain);
    }
}
