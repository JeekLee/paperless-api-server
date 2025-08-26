package com.paperless.api.notification.infrastructure.repository.jpa;

import com.paperless.api.notification.infrastructure.entity.NotificationJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaNotificationRepository extends JpaRepository<NotificationJpaEntity, Long> {
    Optional<NotificationJpaEntity> findByMemberIdAndId(Long memberId, Long id);
}
