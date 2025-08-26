package com.paperless.api.member.presentation.controller;

import com.paperless.api.authentication.dto.UserPrincipal;
import com.paperless.api.member.application.service.MemberInfoService;
import com.paperless.api.member.dto.MemberInfo;
import com.paperless.api.member.presentation.api.MyMemberInfoApi;
import com.paperless.api.member.presentation.request.UpdateImageRequest;
import com.paperless.api.member.presentation.request.UpdateNicknameRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MyMemberInfoController implements MyMemberInfoApi {
    private final MemberInfoService memberInfoService;

    @Override
    public ResponseEntity<MemberInfo> getInfo(UserPrincipal userPrincipal) {
        return ResponseEntity.ok(memberInfoService.getMemberInfo(userPrincipal.getId()));
    }

    @Override
    public ResponseEntity<MemberInfo> updateImage(UserPrincipal userPrincipal, UpdateImageRequest request) {
        return ResponseEntity.ok(memberInfoService.updateProfileImage(userPrincipal.getId(), request.getImageFile()));
    }

    @Override
    public ResponseEntity<MemberInfo> updateNickname(UserPrincipal userPrincipal, UpdateNicknameRequest request) {
        return ResponseEntity.ok(memberInfoService.updateNickname(userPrincipal.getId(), request.getNickname()));
    }
}
