package com.paperless.api.notification.application.repository;

import com.paperless.api.notification.domain.model.Notification;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotificationRepository {
    Notification save(Notification notification);
    Optional<Notification> findByMemberIdAndId(Long memberId, Long id);
}
