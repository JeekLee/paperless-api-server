package com.paperless.api.notification.presentation.controller;

import com.paperless.api.authentication.dto.UserPrincipal;
import com.paperless.api.notification.application.service.NotificationInfoService;
import com.paperless.api.notification.application.service.NotificationService;
import com.paperless.api.notification.presentation.api.MyNotificationApi;
import com.paperless.api.notification.presentation.response.NotificationBoxResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MyNotificationController implements MyNotificationApi {
    private final NotificationService notificationService;
    private final NotificationInfoService notificationInfoService;

    @Override
    public ResponseEntity<NotificationBoxResponse> getBox(UserPrincipal userPrincipal, Pageable pageable) {
        return ResponseEntity.ok(notificationInfoService.getNotificationBox(userPrincipal.getId(), pageable));
    }

    @Override
    public ResponseEntity<Void> readNotification(UserPrincipal userPrincipal, Long notificationId) {
        notificationService.readNotification(userPrincipal.getId(), notificationId);
        return ResponseEntity.noContent().build();
    }
}
