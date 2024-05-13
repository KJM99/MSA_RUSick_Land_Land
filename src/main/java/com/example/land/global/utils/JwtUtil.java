package com.example.land.global.utils;

import com.example.land.global.config.ServerConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

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
    public boolean validateToken(String token) {
        try{
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parse(token);
        }catch (JwtException e){
            return false;
        }
        return true;
    }

    @Autowired
    public JwtUtil(ServerConfig serverConfig) {
        this.secretKey = Keys.hmacShaKeyFor(serverConfig.getSecret().getBytes());
    }
}
