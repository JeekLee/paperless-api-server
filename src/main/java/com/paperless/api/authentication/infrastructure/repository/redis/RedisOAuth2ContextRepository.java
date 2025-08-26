package com.paperless.api.authentication.infrastructure.repository.redis;

import com.paperless.api.authentication.infrastructure.entity.OAuth2ContextRedisEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisOAuth2ContextRepository extends CrudRepository<OAuth2ContextRedisEntity, String> {

}
