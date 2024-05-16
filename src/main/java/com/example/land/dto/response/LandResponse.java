package com.example.land.dto.response;

import com.example.land.domain.entity.Land;

import java.time.LocalDateTime;
import java.util.UUID;

public record LandResponse(
    UUID landId,
    String ownerName,
    String landName,
    int landCategory,
    String landArea,
    String landDescription,
    String landAddress,
    String landDetailAddress,
    long landPrice,
    LocalDateTime landBuiltDate,
    boolean landYN
) {
    public static LandResponse from(Land land) {
        return new LandResponse(
                land.getId(),
                land.getOwnerName(),
                land.getLandName(),
                land.getLandCategory(),
                land.getLandArea(),
                land.getLandDescription(),
                land.getLandAddress(),
                land.getLandDetailAddress(),
                land.getLandPrice(),
                land.getLandBuiltDate(),
                land.getLandYN()
        );
    }

}
