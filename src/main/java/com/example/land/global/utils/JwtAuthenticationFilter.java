package com.example.land.global.utils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String bearerToken = request.getHeader("Authorization");
        System.out.println("aaaa");
        if (bearerToken == null) System.out.println("dasdasd");
        if (bearerToken != null && bearerToken.startsWith("Bearer ") && jwtUtil.validateToken(bearerToken.substring(7))) {
            System.out.println("bbb");
            String token = bearerToken.substring(7);
            TokenInfo tokenInfo = jwtUtil.parseToken(token);
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(tokenInfo, null, tokenInfo.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response);
    }
}
