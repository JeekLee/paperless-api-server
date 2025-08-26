package com.paperless.api.authentication.domain.service;

import com.paperless.api.authentication.domain.model.AccessToken;
import com.paperless.api.authentication.domain.model.RefreshToken;
import com.paperless.api.authentication.dto.UserPrincipal;
import com.paperless.api.authentication.enums.AccessTokenClaim;
import com.paperless.api.authentication.enums.RefreshTokenClaim;
import com.paperless.api.authentication.properties.JwtProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtProvider {
    private final JwtProperties jwtProperties;
    private Key accessTokenKey;
    private Key refreshTokenKey;

    @PostConstruct
    public void init() {
        byte[] accessTokenBytes = Base64.getDecoder().decode(jwtProperties.getAccessToken().getSecretKey());
        accessTokenKey = Keys.hmacShaKeyFor(accessTokenBytes);

        byte[] refreshTokenBytes = Base64.getDecoder().decode(jwtProperties.getRefreshToken().getSecretKey());
        refreshTokenKey = Keys.hmacShaKeyFor(refreshTokenBytes);
    }

    public AccessToken generateAccessToken(UserPrincipal userPrincipal) {
        ZoneId zoneId = ZoneId.of("Asia/Seoul");
        LocalDateTime now = LocalDateTime.now(zoneId);
        LocalDateTime expiresAt = now.plusSeconds(jwtProperties.getAccessToken().getExpiresIn());

        Date expiresAtInDate = Date.from(expiresAt.atZone(zoneId).toInstant());

        String token = Jwts.builder()
                .claim(AccessTokenClaim.ID.getValue(), userPrincipal.getId())
                .claim(AccessTokenClaim.NICKNAME.getValue(), userPrincipal.getNickname())
                .claim(AccessTokenClaim.IMAGE_PATH.getValue(), userPrincipal.getImagePath())
                .claim(AccessTokenClaim.EMAIL.getValue(), userPrincipal.getEmail())
                .claim(AccessTokenClaim.AUTHORITIES.getValue(), userPrincipal.getAuthorities().stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setExpiration(expiresAtInDate)
                .signWith(accessTokenKey, SignatureAlgorithm.HS256)
                .compact();
        log.debug("AccessToken Generated for user {}: {}", userPrincipal.getNickname(), token);

        return AccessToken.builder()
                .token(token)
                .memberId(userPrincipal.getId())
                .nickname(userPrincipal.getNickname())
                .imagePath(userPrincipal.getImagePath())
                .email(userPrincipal.getEmail())
                .expiresAt(expiresAt)
                .build();
    }

    public RefreshToken generateRefreshToken(UserPrincipal userPrincipal) {
        ZoneId zoneId = ZoneId.of("Asia/Seoul");
        LocalDateTime now = LocalDateTime.now(zoneId);
        LocalDateTime expiresAt = now.plusSeconds(jwtProperties.getRefreshToken().getExpiresIn());

        Date expiresAtInDate = Date.from(expiresAt.atZone(zoneId).toInstant());

        String token = Jwts.builder()
                .claim(RefreshTokenClaim.ID.getValue(), userPrincipal.getId())
                .setExpiration(expiresAtInDate)
                .signWith(refreshTokenKey, SignatureAlgorithm.HS256)
                .compact();
        log.debug("RefreshToken Generated for user {}: {}", userPrincipal.getNickname(), token);

        return RefreshToken.builder()
                .token(token)
                .memberId(userPrincipal.getId())
                .expiresAt(expiresAt)
                .build();
    }
}
