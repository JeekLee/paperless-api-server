package com.paperless.api.member.domain.model;

import com.paperless.api.core.error.ExceptionCreator;
import com.paperless.api.member.domain.model.vo.MemberImage;
import com.paperless.api.member.enums.Authority;
import lombok.Builder;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import static com.paperless.api.member.exception.EmailException.INVALID_EMAIL_FORMAT;
import static com.paperless.api.member.exception.EmailException.TOO_LONG_EMAIL;

@Builder
public record Member(
        Long id,
        String nickname,
        String email,
        MemberImage image,
        List<Authority> authorities,

        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

    public static Member create(String nickname, String email, MemberImage image) {
        validateEmail(email);

        return Member.builder()
                .nickname(nickname)
                .email(email)
                .image(image)
                .authorities(Collections.singletonList(Authority.USER))
                .createdAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .updatedAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .build();
    }

    private static void validateEmail(String email) {
        if (email == null) throw ExceptionCreator.create(INVALID_EMAIL_FORMAT, "email: " + email);
        if (!EMAIL_PATTERN.matcher(email).matches())
            throw ExceptionCreator.create(INVALID_EMAIL_FORMAT, "email: " + email);
        if (email.length() > 320) throw ExceptionCreator.create(TOO_LONG_EMAIL, "size: " + email.length());
    }

    public Member updateNickname(String nickname) {
        return Member.builder()
                .id(this.id)
                .nickname(nickname)
                .email(this.email)
                .image(this.image)
                .authorities(this.authorities)
                .createdAt(this.createdAt)
                .updatedAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .build();
    }

    public Member updateProfileImage(MemberImage memberImage) {
        return Member.builder()
                .id(this.id)
                .nickname(this.nickname)
                .email(this.email)
                .image(memberImage)
                .authorities(this.authorities)
                .createdAt(this.createdAt)
                .updatedAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .build();
    }
}
