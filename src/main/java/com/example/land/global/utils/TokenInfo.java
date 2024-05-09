package com.example.land.global.utils;

import io.jsonwebtoken.Claims;

import java.time.LocalDate;

public record TokenInfo(
        String id, String nickname, LocalDate birthDay
) {
    public static TokenInfo fromClaims(Claims claims){
        String id = claims.get("id", String.class);
        String nickname = claims.get("nickname", String.class);
        LocalDate birthDay = claims.get("birthDay", LocalDate.class);
        return new TokenInfo(id, nickname, birthDay);
    }
}