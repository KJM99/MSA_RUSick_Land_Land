package com.example.land.dto.request;

import com.example.land.domain.entity.InterestLand;
import com.example.land.domain.entity.Land;
import com.example.land.global.utils.TokenInfo;

import java.util.UUID;

public record InterestLandRequest(
    String landId, TokenInfo tokenInfo
) {
    public InterestLand toEntity(){
        return InterestLand.builder()
                .userId(UUID.fromString(tokenInfo.id()))
                .land(Land.builder()
                        .id(UUID.fromString(landId))
                        .build())
                .build();
    }
}
