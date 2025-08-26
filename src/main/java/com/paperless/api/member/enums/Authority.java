package com.paperless.api.member.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Authority {
    USER("ROLE_USER"),
    MANAGER("ROLE_MANAGER"),
    ;

    private final String value;
}
