package com.example.land.dto.response;

import com.example.land.domain.entity.SellLog;

public record SellLogResponse(
        String landId,
        Long price
) {
    public static SellLogResponse from(SellLog sellLog) {
        return new SellLogResponse(
                sellLog.getLand().getId().toString(),
                sellLog.getSellLogPrice()
        );
    }
}
