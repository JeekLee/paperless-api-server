package com.paperless.api.authentication.domain.service;

import com.paperless.api.authentication.domain.model.OAuth2UserInfo;
import com.paperless.api.authentication.enums.OAuth2Provider;
import com.paperless.api.authentication.infrastructure.client.GoogleUserProfileClient;
import com.paperless.api.authentication.infrastructure.client.KakaoUserProfileClient;
import com.paperless.api.core.error.ExceptionCreator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.paperless.api.authentication.exception.OAuth2Exception.PROVIDER_NOT_SUPPORTED;

@Service
@Slf4j
@RequiredArgsConstructor
public class OAuth2UserProfileService {
    private final GoogleUserProfileClient googleUserProfileClient;
    private final KakaoUserProfileClient kakaoUserProfileClient;

    public OAuth2UserInfo getUserProfile(OAuth2Provider provider, String accessToken) {
        return switch (provider) {
            case GOOGLE -> googleUserProfileClient.getUserInfo("Bearer " + accessToken);
            case KAKAO -> kakaoUserProfileClient.getUserInfo("Bearer " + accessToken);
            default -> throw ExceptionCreator.create(PROVIDER_NOT_SUPPORTED, "[OAuth2UserProfileService] Provider: " + provider);
        };
    }

}
