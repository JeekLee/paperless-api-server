package com.paperless.api.authentication.infrastructure.repository;

import com.paperless.api.authentication.application.repository.OAuth2ContextRepository;
import com.paperless.api.authentication.domain.model.OAuth2Context;
import com.paperless.api.authentication.infrastructure.entity.OAuth2ContextRedisEntity;
import com.paperless.api.authentication.infrastructure.repository.redis.RedisOAuth2ContextRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OAuth2ContextRepositoryImpl implements OAuth2ContextRepository {
    private final RedisOAuth2ContextRepository redisOAuth2ContextRepository;

    @Override
    public OAuth2Context save(OAuth2Context oAuth2Context) {
        return redisOAuth2ContextRepository.save(OAuth2ContextRedisEntity.fromDomain(oAuth2Context)).toDomain();
    }

    @Override
    public Optional<OAuth2Context>  findByState(String state) {
        return redisOAuth2ContextRepository.findById(state).map(OAuth2ContextRedisEntity::toDomain);
    }
}
