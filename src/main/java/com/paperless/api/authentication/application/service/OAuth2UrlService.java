package com.paperless.api.authentication.application.service;

import com.paperless.api.authentication.application.repository.OAuth2ContextRepository;
import com.paperless.api.authentication.domain.model.OAuth2Context;
import com.paperless.api.authentication.domain.service.CodeChallengeService;
import com.paperless.api.authentication.domain.service.OAuth2ContextService;
import com.paperless.api.authentication.enums.OAuth2Provider;
import com.paperless.api.authentication.presentation.response.OAuth2UrlResponse;
import com.paperless.api.authentication.properties.OAuth2Properties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuth2UrlService {
    private final OAuth2Properties oAuth2Properties;

    private final OAuth2ContextService oAuth2ContextService;
    private final CodeChallengeService codeChallengeService;

    private final OAuth2ContextRepository OAuth2ContextRepository;

    public OAuth2UrlResponse generateAuthUrl(OAuth2Provider oAuth2Provider, String codeVerifier, String redirectPath) {
        String codeChallenge = codeChallengeService.createCodeChallenge(codeVerifier);

        OAuth2Context oauth2Context = oAuth2ContextService.createContext(oAuth2Provider, codeVerifier, redirectPath);
        oauth2Context = OAuth2ContextRepository.save(oauth2Context);

        OAuth2Properties.Provider properties = oAuth2Properties.getProvider(oAuth2Provider);

        URI signInUri = UriComponentsBuilder
                .fromHttpUrl(properties.getAuthorizationUrl())
                .queryParam("client_id", properties.getClientId())
                .queryParam("redirect_uri", properties.getRedirectUri())
                .queryParam("response_type", "code")
                .queryParam("scope", properties.getScope())
                .queryParam("state", oauth2Context.getState())
                .queryParam("code_challenge", codeChallenge)
                .queryParam("code_challenge_method", "S256")
                .build()
                .toUri();

        return OAuth2UrlResponse.builder()
                .url(signInUri.toString())
                .state(oauth2Context.getState())
                .provider(oAuth2Provider)
                .expiresAt(oauth2Context.getExpiresAt())
                .build();
    }
}
