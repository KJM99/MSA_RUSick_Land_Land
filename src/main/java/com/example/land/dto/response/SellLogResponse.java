package com.example.land.dto.response;

import com.example.land.domain.entity.SellLog;

import java.time.LocalDateTime;

public record SellLogResponse(
        LocalDateTime sellDate,
        Long price
) {
    public static SellLogResponse from(SellLog sellLog) {
        return new SellLogResponse(
                sellLog.getSellLogDate(),
                sellLog.getSellLogPrice()
        );
    }
}
