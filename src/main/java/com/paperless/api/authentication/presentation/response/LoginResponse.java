package com.paperless.api.authentication.presentation.response;

import com.paperless.api.authentication.dto.AuthenticationContext;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginResponse {
    private final String redirectPath;

    @Builder(access = AccessLevel.PRIVATE)
    public LoginResponse(String redirectPath) {
        this.redirectPath = redirectPath;
    }

    public static LoginResponse fromAuthenticationContext(AuthenticationContext context) {
        return LoginResponse.builder()
                .redirectPath(context.getRedirectPath())
                .build();
    }
}
