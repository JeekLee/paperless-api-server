package com.paperless.api.notification.infrastructure.repository;

import com.paperless.api.notification.application.repository.NotificationRepository;
import com.paperless.api.notification.domain.model.Notification;
import com.paperless.api.notification.infrastructure.entity.NotificationJpaEntity;
import com.paperless.api.notification.infrastructure.repository.jpa.JpaNotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepository {
    private final JpaNotificationRepository jpaNotificationRepository;

    @Override
    public Notification save(Notification notification) {
        return jpaNotificationRepository.save(NotificationJpaEntity.fromDomain(notification)).toDomain();
    }

    @Override
    public Optional<Notification> findByMemberIdAndId(Long memberId, Long id) {
        return jpaNotificationRepository.findByMemberIdAndId(memberId, id).map(NotificationJpaEntity::toDomain);
    }
}
