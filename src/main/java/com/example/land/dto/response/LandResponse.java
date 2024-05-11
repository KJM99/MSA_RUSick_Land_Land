package com.example.land.dto.response;

import com.example.land.domain.entity.Land;

import java.time.LocalDateTime;

public record LandResponse(
    String ownerName,
    String landName,
    int landCategory,
    String landArea,
    String landDescription,
    String landAddress,
    String landDetailAddress,
    long landPrice,
    LocalDateTime landBuiltDate
) {
    public static LandResponse from(Land land) {
        return new LandResponse(
                land.getOwnerName(),
                land.getLandName(),
                land.getLandCategory(),
                land.getLandArea(),
                land.getLandDescription(),
                land.getLandAddress(),
                land.getLandDetailAddress(),
                land.getLandPrice(),
                land.getLandBuiltDate()
        );
    }

}
