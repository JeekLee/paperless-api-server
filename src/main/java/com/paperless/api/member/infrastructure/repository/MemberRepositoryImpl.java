package com.paperless.api.member.infrastructure.repository;

import com.paperless.api.member.application.repository.MemberRepository;
import com.paperless.api.member.domain.model.Member;
import com.paperless.api.member.infrastructure.entity.MemberJpaEntity;
import com.paperless.api.member.infrastructure.repository.jpa.JpaMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {
    private final JpaMemberRepository jpaMemberRepository;

    @Override
    public Optional<Member> findByEmail(String email) {
        return jpaMemberRepository.findByEmail(email).map(MemberJpaEntity::toDomain);
    }

    @Override
    public Member save(Member member) {
        return jpaMemberRepository.save(MemberJpaEntity.fromDomain(member)).toDomain();
    }

    @Override
    public Optional<Member> findById(Long id) {
        return jpaMemberRepository.findById(id).map(MemberJpaEntity::toDomain);
    }

    @Override
    public Optional<Member> findByNickname(String nickname) {
        return jpaMemberRepository.findByNickname(nickname).map(MemberJpaEntity::toDomain);
    }
}
