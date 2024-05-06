package com.example.land.dto;

import com.example.land.global.domain.entity.Land;
import com.example.land.global.domain.entity.SellLog;

import java.time.LocalDateTime;

public record SellLogRequest(
        Long landId,
        LocalDateTime sellLogDate,
        Long sellLogPrice
) {
    public SellLog toEntity(){
        return SellLog.builder()
                .land(Land.builder()
                        .id(landId)
                        .build())
                .sellLogDate(LocalDateTime.now())
                .sellLogPrice(sellLogPrice)
                .build();
    }
}
