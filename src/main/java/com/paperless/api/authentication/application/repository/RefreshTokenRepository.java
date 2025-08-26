package com.paperless.api.authentication.application.repository;

import com.paperless.api.authentication.domain.model.RefreshToken;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository {
    RefreshToken save(RefreshToken refreshToken);
    void deleteByMemberId(Long memberId);
    Optional<RefreshToken> findByMemberId(Long memberId);
}
