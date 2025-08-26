package com.paperless.api.notification.infrastructure.dao;

import com.paperless.api.notification.application.dao.NotificationInfoDao;
import com.paperless.api.notification.dto.NotificationInfo;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.paperless.api.notification.infrastructure.entity.QNotificationJpaEntity.notificationJpaEntity;

@Repository
@RequiredArgsConstructor
public class NotificationInfoDaoImpl implements NotificationInfoDao {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<NotificationInfo> findByMemberId(Long memberId, Pageable pageable) {
        JPAQuery<NotificationInfo> query = jpaQueryFactory
                .select(Projections.constructor(NotificationInfo.class,
                        notificationJpaEntity.id,
                        notificationJpaEntity.message,
                        notificationJpaEntity.isRead,
                        notificationJpaEntity.redirectPath,
                        notificationJpaEntity.createdAt,
                        notificationJpaEntity.updatedAt
                ))
                .from(notificationJpaEntity)
                .where(notificationJpaEntity.memberId.eq(memberId))
                .orderBy(notificationJpaEntity.isRead.asc(), notificationJpaEntity.createdAt.desc());

        List<NotificationInfo> results = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = jpaQueryFactory
                .select(notificationJpaEntity.count())
                .from(notificationJpaEntity)
                .where(notificationJpaEntity.memberId.eq(memberId))
                .fetchOne();

        return new PageImpl<>(results, pageable, total != null ? total : 0);
    }

    @Override
    public Long countUnreadNotifications(Long memberId) {
        return jpaQueryFactory
                .select(notificationJpaEntity.count())
                .from(notificationJpaEntity)
                .where(notificationJpaEntity.memberId.eq(memberId))
                .where(notificationJpaEntity.isRead.eq(false))
                .fetchOne();
    }
}
