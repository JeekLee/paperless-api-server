package com.paperless.api.authentication.dto;

import com.paperless.api.authentication.domain.model.AccessToken;
import com.paperless.api.authentication.domain.model.RefreshToken;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationContext {
    private AccessToken accessToken;
    private RefreshToken refreshToken;

    private String redirectPath;
}
