package com.paperless.api.authentication.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RefreshTokenClaim {
    ID("id"),
    ;

    private final String value;
}
