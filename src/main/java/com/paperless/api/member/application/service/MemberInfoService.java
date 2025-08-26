package com.paperless.api.member.application.service;

import com.paperless.api.core.error.ExceptionCreator;
import com.paperless.api.member.application.dao.MemberInfoDao;
import com.paperless.api.member.application.repository.MemberRepository;
import com.paperless.api.member.domain.model.Member;
import com.paperless.api.member.domain.model.vo.MemberImage;
import com.paperless.api.member.domain.service.MemberImageService;
import com.paperless.api.member.domain.service.NicknameGenerator;
import com.paperless.api.member.dto.MemberInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static com.paperless.api.member.exception.MemberException.MEMBER_NOT_FOUND;
import static com.paperless.api.member.exception.MemberException.NICKNAME_ALREADY_EXISTS;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberInfoService {
    private final MemberRepository memberRepository;
    private final MemberInfoDao memberInfoDao;
    private final NicknameGenerator nicknameGenerator;
    private final MemberImageService memberImageService;

    @Transactional(readOnly = true)
    public MemberInfo getMemberInfo(Long memberId) {
        return memberInfoDao.findByMemberId(memberId)
                .orElseThrow(() -> ExceptionCreator.create(MEMBER_NOT_FOUND, "memberId: " + memberId));
    }

    @Transactional
    public MemberInfo updateNickname(Long memberId, String nickname) {
        String nicknameWithRandomNumber = nicknameGenerator.generate(nickname);
        Optional<Member> existingMember = memberRepository.findByNickname(nicknameWithRandomNumber);
        if (existingMember.isPresent()) {
            throw ExceptionCreator.create(NICKNAME_ALREADY_EXISTS, "nickname: " + nicknameWithRandomNumber);
        }

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> ExceptionCreator.create(MEMBER_NOT_FOUND, "memberId: " + memberId));

        member = memberRepository.save(member.updateNickname(nicknameWithRandomNumber));
        return MemberInfo.builder()
                .id(member.id())
                .email(member.email())
                .nickname(member.nickname())
                .imagePath(member.image().path())
                .build();
    }

    @Transactional
    public MemberInfo updateProfileImage(Long memberId, MultipartFile profileImageFile) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> ExceptionCreator.create(MEMBER_NOT_FOUND, "memberId: " + memberId));

        MemberImage memberImage = memberImageService.create(profileImageFile);
        memberImageService.delete(member.image());

        member = memberRepository.save(member.updateProfileImage(memberImage));
        return MemberInfo.builder()
                .id(member.id())
                .email(member.email())
                .nickname(member.nickname())
                .imagePath(member.image().path())
                .build();
    }
}
