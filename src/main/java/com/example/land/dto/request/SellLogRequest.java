package com.example.land.dto.request;

import com.example.land.domain.entity.Land;
import com.example.land.domain.entity.SellLog;
import com.example.land.global.utils.TokenInfo;

import java.time.LocalDateTime;
import java.util.UUID;

public record SellLogRequest(
        String buyerEmail,
        Long sellLogPrice
) {
    public SellLog toEntity(String landId, TokenInfo tokenInfo){
        return SellLog.builder()
                .id(UUID.fromString(tokenInfo.id()))
                .land(Land.builder()
                        .id(UUID.fromString(landId))
                        .build())
                .sellLogDate(LocalDateTime.now())
                .sellLogPrice(sellLogPrice)
                .build();
    }
}
