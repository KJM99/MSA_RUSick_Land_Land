package com.example.land.dto.response;

import com.example.land.domain.entity.InterestLand;
import com.example.land.domain.entity.Land;

public record InterestLandResponse(
    String landName,
    Long landPrice,
    int landCategory,
    String landArea,
    String landDescription
) {
    public static InterestLandResponse from(Land land){
        return new InterestLandResponse(
            land.getLandName(),
            land.getLandPrice(),
            land.getLandCategory(),
            land.getLandArea(),
            land.getLandDescription()
        );
    }
}