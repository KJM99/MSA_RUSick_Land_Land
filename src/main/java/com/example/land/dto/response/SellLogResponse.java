package com.example.land.dto.response;

import com.example.land.domain.entity.SellLog;

import java.time.LocalDateTime;

public record SellLogResponse(
        String landId,
        LocalDateTime sellLogDate,
        Long price
) {
    public static SellLogResponse from(SellLog sellLog) {
        return new SellLogResponse(
                sellLog.getLand().getId().toString(),
                sellLog.getSellLogDate(),
                sellLog.getSellLogPrice()
        );
    }
}
