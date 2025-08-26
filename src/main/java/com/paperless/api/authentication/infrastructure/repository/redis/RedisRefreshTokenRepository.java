package com.paperless.api.authentication.infrastructure.repository.redis;

import com.paperless.api.authentication.infrastructure.entity.RefreshTokenRedisEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisRefreshTokenRepository extends CrudRepository<RefreshTokenRedisEntity, String> {

}
