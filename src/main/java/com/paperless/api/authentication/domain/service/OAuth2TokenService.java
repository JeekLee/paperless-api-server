package com.paperless.api.authentication.domain.service;

import com.paperless.api.authentication.domain.model.OAuth2AccessToken;
import com.paperless.api.authentication.enums.OAuth2Provider;
import com.paperless.api.authentication.infrastructure.client.GoogleTokenClient;
import com.paperless.api.authentication.infrastructure.client.KakaoTokenClient;
import com.paperless.api.authentication.properties.OAuth2Properties;
import com.paperless.api.core.error.ExceptionCreator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.paperless.api.authentication.exception.OAuth2Exception.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class OAuth2TokenService {
    private final OAuth2Properties oAuth2Properties;
    private final GoogleTokenClient googleTokenClient;
    private final KakaoTokenClient kakaoTokenClient;

    public OAuth2AccessToken getAccessToken(OAuth2Provider provider, String code, String codeVerifier) {
        return switch (provider) {
            case GOOGLE -> googleTokenClient.exchangeCodeForAccessToken(
                    code,
                    oAuth2Properties.getGoogle().getClientId(),
                    oAuth2Properties.getGoogle().getClientSecret(),
                    oAuth2Properties.getGoogle().getRedirectUri(),
                    "authorization_code",
                    codeVerifier
            );
            case KAKAO -> kakaoTokenClient.exchangeCodeForToken(
                    code,
                    oAuth2Properties.getKakao().getClientId(),
                    oAuth2Properties.getKakao().getClientSecret(),
                    oAuth2Properties.getKakao().getRedirectUri(),
                    "authorization_code",
                    codeVerifier
            );
            default -> throw ExceptionCreator.create(PROVIDER_NOT_SUPPORTED, "[OAuth2TokenService] Provider: " + provider);
        };
    }
}
