package com.paperless.api.notification.domain.model;

import lombok.Builder;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Builder
public record Notification(
        Long id,
        Long memberId,
        String message,
        String redirectPath,
        Boolean isRead,

        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static Notification create(Long memberId, String message, String redirectPath) {
        return Notification.builder()
                .memberId(memberId)
                .message(message)
                .redirectPath(redirectPath)
                .isRead(false)
                .createdAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .updatedAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .build();
    }

    public Notification read() {
        return Notification.builder()
                .id(this.id)
                .memberId(this.memberId)
                .message(this.message)
                .redirectPath(this.redirectPath)
                .isRead(true)
                .createdAt(this.createdAt)
                .updatedAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .build();
    }
}
