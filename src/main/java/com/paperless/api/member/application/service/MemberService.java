package com.paperless.api.member.application.service;

import com.paperless.api.core.error.ExceptionCreator;
import com.paperless.api.member.domain.model.vo.MemberImage;
import com.paperless.api.member.domain.service.MemberImageService;
import com.paperless.api.member.application.repository.MemberRepository;
import com.paperless.api.member.domain.model.Member;
import com.paperless.api.member.domain.service.NicknameGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.paperless.api.member.exception.MemberException.MEMBER_NOT_FOUND;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final NicknameGenerator nicknameGenerator;
    private final MemberImageService memberImageService;

    @Transactional
    public Member getOrCreateMember(String email, String imageUrl) {
        return memberRepository.findByEmail(email)
                .orElseGet(() -> createMember(email, imageUrl));
    }

    private Member createMember(String email, String imageUrl) {
        MemberImage memberImage = (imageUrl != null) ? memberImageService.create(imageUrl) : null;
        Member member = Member.create(nicknameGenerator.generate(), email, memberImage);
        return memberRepository.save(member);
    }

    @Transactional(readOnly = true)
    public Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> ExceptionCreator.create(MEMBER_NOT_FOUND, "memberId: " + memberId));
    }
}
