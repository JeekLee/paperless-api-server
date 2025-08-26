package com.paperless.api.notification.presentation.controller;

import com.paperless.api.notification.application.service.NotificationService;
import com.paperless.api.notification.presentation.api.NotificationApi;
import com.paperless.api.notification.presentation.request.SendNotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NotificationController implements NotificationApi {
    private final NotificationService notificationService;

    @Override
    public ResponseEntity<Void> sendNotification(SendNotificationRequest request) {
        notificationService.sendNotification(request.getMemberId(), request.getMessage(), request.getRedirectPath());
        return ResponseEntity.noContent().build();
    }
}
