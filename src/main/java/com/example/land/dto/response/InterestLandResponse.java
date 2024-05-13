package com.example.land.dto.response;

import com.example.land.domain.entity.InterestLand;

public record InterestLandResponse(
        String id,
        String landName,
        Long landPrice,
        int landCategory,
        String landArea,
        String landDescription
) {
    public static InterestLandResponse from(InterestLand interestLand){
        return new InterestLandResponse(
            interestLand.getLand().getId().toString(),
            interestLand.getLand().getLandName(),
            interestLand.getLand().getLandPrice(),
            interestLand.getLand().getLandCategory(),
            interestLand.getLand().getLandArea(),
            interestLand.getLand().getLandDescription()
        );
    }
}