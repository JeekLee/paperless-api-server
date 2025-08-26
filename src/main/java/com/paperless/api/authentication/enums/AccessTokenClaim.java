package com.paperless.api.authentication.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AccessTokenClaim {
    ID("id"),
    NICKNAME("nickname"),
    IMAGE_PATH("image_path"),
    EMAIL("email"),
    AUTHORITIES("authorities");

    private final String value;
}
