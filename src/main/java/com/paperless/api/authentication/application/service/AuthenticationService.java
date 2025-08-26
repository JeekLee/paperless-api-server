package com.paperless.api.authentication.application.service;

import com.paperless.api.authentication.application.repository.OAuth2ContextRepository;
import com.paperless.api.authentication.application.repository.RefreshTokenRepository;
import com.paperless.api.authentication.domain.model.*;
import com.paperless.api.authentication.domain.service.JwtProvider;
import com.paperless.api.authentication.domain.service.JwtResolver;
import com.paperless.api.authentication.domain.service.OAuth2TokenService;
import com.paperless.api.authentication.domain.service.OAuth2UserProfileService;
import com.paperless.api.authentication.dto.AuthenticationContext;
import com.paperless.api.authentication.dto.UserPrincipal;
import com.paperless.api.authentication.enums.OAuth2Provider;
import com.paperless.api.authentication.exception.RefreshTokenException;
import com.paperless.api.core.error.ExceptionCreator;
import com.paperless.api.member.application.service.MemberService;
import com.paperless.api.member.domain.model.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.paperless.api.authentication.exception.OAuth2Exception.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {
    private final OAuth2TokenService oAuth2TokenService;
    private final OAuth2UserProfileService oAuth2UserProfileService;
    private final MemberService memberService;
    private final JwtProvider jwtProvider;
    private final JwtResolver jwtResolver;

    private final OAuth2ContextRepository OAuth2ContextRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public AuthenticationContext oauth2Login(OAuth2Provider provider, String code, String state) {
        OAuth2Context oAuth2Context = OAuth2ContextRepository.findByState(state)
                .orElseThrow(() -> ExceptionCreator.create(OAUTH2_CONTEXT_NOT_FOUND, "state: " + state));

        // TODO: FeignClient 발생 예외 처리 방법 구상
        OAuth2AccessToken oAuth2AccessToken;
        OAuth2UserInfo oAuth2UserInfo;
        try {
            oAuth2AccessToken = oAuth2TokenService.getAccessToken(provider, code, oAuth2Context.getCodeVerifier());
        }
        catch (Exception e) {
            throw ExceptionCreator.create(FAILED_TO_GET_ACCESS_TOKEN, "OAuth2 Provider message: " + e.getMessage());
        }

        try {
            oAuth2UserInfo = oAuth2UserProfileService.getUserProfile(provider, oAuth2AccessToken.getAccessToken());
        }
        catch (Exception e) {
            throw ExceptionCreator.create(FAILED_TO_GET_USER_PROFILE, "OAuth2 Provider message: " + e.getMessage());
        }

        Member member = memberService.getOrCreateMember(oAuth2UserInfo.getEmail(), oAuth2UserInfo.getImageUrl());
        UserPrincipal userPrincipal = UserPrincipal.create(member);

        AccessToken accessToken = jwtProvider.generateAccessToken(userPrincipal);
        RefreshToken refreshToken = jwtProvider.generateRefreshToken(userPrincipal);

        refreshTokenRepository.save(refreshToken);

        return AuthenticationContext.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .redirectPath(oAuth2Context.getRedirectPath())
                .build();
    }

    @Transactional
    public void logOut(Long memberId) {
        refreshTokenRepository.deleteByMemberId(memberId);
    }

    @Transactional
    public AuthenticationContext refreshAuthenticationToken(String token) {
        Long memberId = jwtResolver.getMemberIdFromRefreshToken(token);

        RefreshToken refreshToken = refreshTokenRepository.findByMemberId(memberId)
                .orElseThrow(() -> ExceptionCreator.create(RefreshTokenException.REFRESH_TOKEN_NOT_FOUND, "memberId: " + memberId));

        refreshToken.validateRefreshRequest(token);

        RefreshToken newRefreshToken = refreshToken.increaseUseCount();
        refreshTokenRepository.save(newRefreshToken);

        Member member = memberService.getMember(memberId);
        UserPrincipal userPrincipal = UserPrincipal.create(member);

        AccessToken accessToken = jwtProvider.generateAccessToken(userPrincipal);

        return AuthenticationContext.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}