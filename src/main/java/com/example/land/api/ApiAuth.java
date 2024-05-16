package com.example.land.api;

import com.example.land.dto.response.UserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ApiAuth {
    public final FeignAuth feignAuth;

    public UserInfoResponse getUserIdByEmail(String email) {
        return feignAuth.getUserIdByEmail(email);
    }
}
