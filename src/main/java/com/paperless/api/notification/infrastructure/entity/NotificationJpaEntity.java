package com.paperless.api.notification.infrastructure.entity;

import com.paperless.api.core.entity.TimeStamped;
import com.paperless.api.notification.domain.model.Notification;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "notification", catalog = "account")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotificationJpaEntity extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "member_id", nullable = false)
    Long memberId;

    @Column(name = "message", nullable = false)
    String message;

    @Column(name = "redirect_path")
    String redirectPath;

    @Column(name = "is_read", nullable = false)
    Boolean isRead;

    @Builder(access = AccessLevel.PRIVATE)
    public NotificationJpaEntity(Long id, Long memberId, String message, String redirectPath, Boolean isRead) {
        this.id = id;
        this.memberId = memberId;
        this.message = message;
        this.redirectPath = redirectPath;
        this.isRead = isRead;
    }

    public Notification toDomain() {
        return Notification.builder()
                .id(this.id)
                .memberId(this.memberId)
                .message(this.message)
                .redirectPath(this.redirectPath)
                .isRead(this.isRead)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();
    }

    public static NotificationJpaEntity fromDomain(Notification notification) {
        return NotificationJpaEntity.builder()
                .id(notification.id())
                .memberId(notification.memberId())
                .message(notification.message())
                .redirectPath(notification.redirectPath())
                .isRead(notification.isRead())
                .build();
    }
}
