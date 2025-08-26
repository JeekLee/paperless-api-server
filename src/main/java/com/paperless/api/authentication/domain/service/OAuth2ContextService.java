package com.paperless.api.authentication.domain.service;

import com.paperless.api.authentication.domain.model.OAuth2Context;
import com.paperless.api.authentication.enums.OAuth2Provider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
@Slf4j
@RequiredArgsConstructor
public class OAuth2ContextService {
    private static final SecureRandom secureRandom = new SecureRandom();

    public OAuth2Context createContext(OAuth2Provider provider, String codeVerifier, String redirectPath) {
        byte[] bytes = new byte[32];
        secureRandom.nextBytes(bytes);
        String state = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);

        return OAuth2Context.builder()
                .state(state)
                .provider(provider)
                .codeVerifier(codeVerifier)
                .redirectPath(redirectPath)
                .build();
    }
}
