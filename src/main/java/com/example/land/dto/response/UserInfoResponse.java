package com.example.land.dto.response;

import java.util.UUID;

public record UserInfoResponse(
        UUID userId,
        String userName
) {
}
