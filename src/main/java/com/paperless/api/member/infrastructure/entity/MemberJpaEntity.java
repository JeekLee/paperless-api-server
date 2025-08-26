package com.paperless.api.member.infrastructure.entity;

import com.paperless.api.core.entity.TimeStamped;
import com.paperless.api.member.domain.model.Member;
import com.paperless.api.member.domain.model.vo.MemberImage;
import com.paperless.api.member.enums.Authority;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "member", catalog = "account")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberJpaEntity extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nickname", unique = true, nullable = false)
    private String nickname;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "image_path")
    private String imagePath;

    @ElementCollection(targetClass = Authority.class, fetch = FetchType.LAZY)
    @CollectionTable(
            name = "member_authority",
            catalog = "account",
            joinColumns = @JoinColumn(name = "member_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"member_id", "authority"})
    )
    @Enumerated(EnumType.STRING)
    @Column(
            name = "authority",
            nullable = false,
            columnDefinition = "VARCHAR(50) CHECK (authority IN ('USER', 'EXPERT', 'MANAGER'))"
    )
    private List<Authority> authorities = new ArrayList<>();

    @Builder(access = AccessLevel.PRIVATE)
    private MemberJpaEntity(Long id, String nickname, String email, String imagePath, List<Authority> authorities) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.imagePath = imagePath;
        this.authorities = authorities;
    }

    public Member toDomain() {
        return Member.builder()
                .id(this.id)
                .nickname(this.nickname)
                .email(this.email)
                .image(new MemberImage(this.imagePath))
                .authorities(this.authorities)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();
    }

    public static MemberJpaEntity fromDomain(Member member) {
        return MemberJpaEntity.builder()
                .id(member.id())
                .nickname(member.nickname())
                .email(member.email())
                .imagePath(member.image().path())
                .authorities(member.authorities())
                .build();
    }
}
