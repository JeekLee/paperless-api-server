package com.paperless.api.member.application.dao;

import com.paperless.api.member.dto.MemberInfo;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberInfoDao {
    Optional<MemberInfo> findByMemberId(Long memberId);
}
