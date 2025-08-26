package com.paperless.api.authentication.domain.service;

import com.paperless.api.authentication.dto.UserPrincipal;
import com.paperless.api.authentication.enums.AccessTokenClaim;
import com.paperless.api.authentication.enums.RefreshTokenClaim;
import com.paperless.api.authentication.exception.AuthorizationException;
import com.paperless.api.authentication.exception.RefreshTokenException;
import com.paperless.api.authentication.properties.JwtProperties;
import com.paperless.api.core.error.ExceptionCreator;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.DecodingException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtResolver {
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

    public UserPrincipal getUserPrincipalFromAccessToken(String token) {
        try {
            if (token == null) throw ExceptionCreator.create(AuthorizationException.ACCESS_TOKEN_NOT_FOUND);

            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(accessTokenKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            List<String> authoritiesAsString = claims.get(AccessTokenClaim.AUTHORITIES.getValue(), List.class);

            Collection<GrantedAuthority> authorities = authoritiesAsString.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            return new UserPrincipal(
                    claims.get(AccessTokenClaim.ID.getValue(), Long.class),
                    claims.get(AccessTokenClaim.NICKNAME.getValue(), String.class),
                    claims.get(AccessTokenClaim.IMAGE_PATH.getValue(), String.class),
                    claims.get(AccessTokenClaim.EMAIL.getValue(), String.class),
                    authorities
            );
        } catch (SecurityException | UnsupportedJwtException | SignatureException | MalformedJwtException | DecodingException e) {
            throw ExceptionCreator.create(AuthorizationException.ACCESS_TOKEN_INVALID, "AccessToken: " + token);
        } catch (ExpiredJwtException e) {
            throw ExceptionCreator.create(AuthorizationException.ACCESS_TOKEN_EXPIRED, "AccessToken: " + token);
        }
    }

    public Long getMemberIdFromRefreshToken(String token) {
        try {
            if (token == null) throw ExceptionCreator.create(RefreshTokenException.REFRESH_TOKEN_NOT_FOUND);

            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(refreshTokenKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return claims.get(RefreshTokenClaim.ID.getValue(), Long.class);
        } catch (SecurityException | UnsupportedJwtException | SignatureException | MalformedJwtException | DecodingException e) {
            throw ExceptionCreator.create(RefreshTokenException.REFRESH_TOKEN_INVALID, "RefreshToken: " + token);
        } catch (ExpiredJwtException e) {
            throw ExceptionCreator.create(RefreshTokenException.REFRESH_TOKEN_EXPIRED, "RefreshToken: " + token);
        }
    }
}
