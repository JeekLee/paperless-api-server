package com.paperless.api.authentication.dto;

import com.paperless.api.member.domain.model.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.security.auth.Subject;
import java.security.Principal;
import java.util.Collection;
import java.util.List;

@Getter
@AllArgsConstructor
public class UserPrincipal implements UserDetails, Principal {
    private final Long id;
    private final String nickname;
    private final String imagePath;
    private final String email;
    private final Collection<GrantedAuthority> authorities;

    public static UserPrincipal create(Member member) {
        List<GrantedAuthority> grantedAuthorities = member.authorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getValue()))
                .map(authority -> (GrantedAuthority) authority)
                .toList();

        return new UserPrincipal(
                member.id(),
                member.nickname(),
                member.image().path(),
                member.email(),
                grantedAuthorities
        );
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.nickname;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public String getName() {
        return this.id.toString();
    }

    @Override
    public boolean implies(Subject subject) {
        return Principal.super.implies(subject);
    }
}
