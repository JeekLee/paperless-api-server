package com.paperless.api.notification.application.service;

import com.paperless.api.notification.application.dao.NotificationInfoDao;
import com.paperless.api.notification.presentation.response.NotificationBoxResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NotificationInfoService {
    private final NotificationInfoDao notificationInfoDao;

    @Transactional(readOnly = true)
    public NotificationBoxResponse getNotificationBox(Long memberId, Pageable pageable) {
        return NotificationBoxResponse.builder()
                .notifications(notificationInfoDao.findByMemberId(memberId, pageable))
                .unreadCount(notificationInfoDao.countUnreadNotifications(memberId))
                .build();
    }
}
