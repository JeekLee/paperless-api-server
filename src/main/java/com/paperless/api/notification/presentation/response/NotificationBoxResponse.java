package com.paperless.api.notification.presentation.response;

import com.paperless.api.notification.dto.NotificationInfo;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
@Builder
public class NotificationBoxResponse {
    Page<NotificationInfo> notifications;
    Long unreadCount;
}
