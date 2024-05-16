package com.example.land.api;

import com.example.land.dto.response.UserInfoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@FeignClient(value = "AUTH", path = "/api/v1/auth")
public interface FeignAuth {
    @PostMapping()
    UserInfoResponse getUserIdByEmail(@RequestBody String email);
}
