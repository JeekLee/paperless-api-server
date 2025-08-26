package com.paperless.api.authentication.application.repository;

import com.paperless.api.authentication.domain.model.OAuth2Context;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OAuth2ContextRepository {
    OAuth2Context save(OAuth2Context state);
    Optional<OAuth2Context> findByState(String state);
}
