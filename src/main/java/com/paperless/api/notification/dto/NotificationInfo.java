package com.paperless.api.notification.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class NotificationInfo {
    private Long id;
    private String message;
    private Boolean isRead;
    private String redirectPath;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public NotificationInfo(Long id, String message, Boolean isRead, String redirectPath, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.message = message;
        this.isRead = isRead;
        this.redirectPath = redirectPath;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
