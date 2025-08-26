package com.paperless.api.notification.application.dao;

import com.paperless.api.notification.dto.NotificationInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationInfoDao {
    Page<NotificationInfo> findByMemberId(Long memberId, Pageable pageable);
    Long countUnreadNotifications(Long memberId);
}
