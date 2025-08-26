package com.paperless.api.member.infrastructure.dao;

import com.paperless.api.member.application.dao.MemberInfoDao;
import com.paperless.api.member.dto.MemberInfo;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.paperless.api.member.infrastructure.entity.QMemberJpaEntity.memberJpaEntity;

@Repository
@RequiredArgsConstructor
public class MemberInfoDaoImpl implements MemberInfoDao {
    private final JPAQueryFactory jpaQueryFactory;

    public Optional<MemberInfo> findByMemberId(Long memberId) {
        MemberInfo memberInfo = jpaQueryFactory
                .select(Projections.constructor(MemberInfo.class,
                        memberJpaEntity.id,
                        memberJpaEntity.nickname,
                        memberJpaEntity.imagePath,
                        memberJpaEntity.email
                ))
                .from(memberJpaEntity)
                .where(memberJpaEntity.id.eq(memberId))
                .fetchOne();

        return Optional.ofNullable(memberInfo);
    }
}
