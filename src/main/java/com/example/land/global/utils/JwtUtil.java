package com.example.land.global.utils;

import com.example.land.global.config.ServerConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {
    private final SecretKey secretKey;


    public TokenInfo parseToken(String token) {
        Claims payload = (Claims) Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parse(token)
                .getPayload();
        return TokenInfo.fromClaims(payload);
    }



    @Autowired
    public JwtUtil(ServerConfig serverConfig) {
        this.secretKey = Keys.hmacShaKeyFor(serverConfig.getSecret().getBytes());
    }
}
