package com.example.land.dto;

import com.example.land.domain.entity.Land;
import com.example.land.domain.entity.SellLog;

import java.time.LocalDateTime;
import java.util.UUID;

public record SellLogRequest(
        String landId,
        LocalDateTime sellLogDate,
        Long sellLogPrice
) {
    public SellLog toEntity(){
        return SellLog.builder()
                .land(Land.builder()
                        .id(UUID.fromString(landId))
                        .build())
                .sellLogDate(LocalDateTime.now())
                .sellLogPrice(sellLogPrice)
                .build();
    }
}
