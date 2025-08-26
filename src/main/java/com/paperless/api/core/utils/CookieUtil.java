package com.paperless.api.core.utils;

import org.springframework.http.ResponseCookie;

import java.time.Duration;
import java.time.LocalDateTime;

public class CookieUtil {
    public static ResponseCookie createCookie(String key, String value, Long maxAge) {
        return ResponseCookie.from(key, value)
                .path("/")
                .sameSite("None")
                .httpOnly(true)
                .secure(true)
                .maxAge(maxAge)
                .build();
    }
    public static ResponseCookie createCookie(String key, String value, LocalDateTime expiresAt) {
        long maxAge = Duration.between(LocalDateTime.now(), expiresAt).getSeconds();

        return ResponseCookie.from(key, value)
                .path("/")
                .sameSite("None")
                .httpOnly(true)
                .secure(true)
                .maxAge(maxAge)
                .build();
    }
    public static ResponseCookie createEmptyCookie(String key) {
        return ResponseCookie.from(key, "")
                .path("/")
                .sameSite("None")
                .httpOnly(true)
                .secure(true)
                .maxAge(0)
                .build();
    }
}
