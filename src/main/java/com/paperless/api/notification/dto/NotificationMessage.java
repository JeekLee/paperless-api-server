package com.paperless.api.notification.dto;

import com.paperless.api.notification.domain.model.Notification;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
public class NotificationMessage implements Serializable {
    private Long memberId;
    private Long notificationId;
    private String redirectPath;
    private String message;

    public static NotificationMessage create(Notification notification) {
        return NotificationMessage.builder()
                .memberId(notification.memberId())
                .notificationId(notification.id())
                .redirectPath(notification.redirectPath())
                .message(notification.message())
                .build();
    }
}
