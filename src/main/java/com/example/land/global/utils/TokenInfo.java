package com.example.land.global.utils;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public record TokenInfo(
        String id, String nickname, LocalDate birthDay
) implements UserDetails {
    public static TokenInfo fromClaims(Claims claims){
        String id = claims.get("id", String.class);
        String nickname = claims.get("nickname", String.class);
        LocalDate birthDay = LocalDate.parse(claims.get("birthDay", String.class));
        return new TokenInfo(id, nickname, birthDay);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return id;
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
}