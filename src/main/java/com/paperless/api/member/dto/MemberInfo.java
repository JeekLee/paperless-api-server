package com.paperless.api.member.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberInfo {
    private Long id;
    private String nickname;
    private String imagePath;
    private String email;

    @Builder
    public MemberInfo(Long id, String nickname, String imagePath, String email) {
        this.id = id;
        this.nickname = nickname;
        this.imagePath = imagePath;
        this.email = email;
    }
}
