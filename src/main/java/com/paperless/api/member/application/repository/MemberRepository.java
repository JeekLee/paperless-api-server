package com.paperless.api.member.application.repository;

import com.paperless.api.member.domain.model.Member;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository {
    Optional<Member> findByEmail(String email);
    Member save(Member member);
    Optional<Member> findById(Long id);
    Optional<Member> findByNickname(String nickname);
}
