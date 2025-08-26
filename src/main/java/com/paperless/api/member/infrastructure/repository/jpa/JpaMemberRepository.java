package com.paperless.api.member.infrastructure.repository.jpa;

import com.paperless.api.member.infrastructure.entity.MemberJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaMemberRepository extends JpaRepository<MemberJpaEntity, Long>{
    Optional<MemberJpaEntity> findByEmail(String email);
    Optional<MemberJpaEntity> findByNickname(String nickname);
}
