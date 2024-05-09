package com.example.land.dto;

import com.example.land.global.domain.entity.Land;

import java.time.LocalDateTime;
import java.util.UUID;

public record LandCreateRequest(

        String ownerId,
        String ownerName,
        String landName,
        int landCategory,
        String landArea,
        String landDescription,
        String landAddress,
        String landDetailAddress,
        Long landPrice,
        LocalDateTime landBuiltDate

        // 매물 여부는 등록시 true로 변경(서비스에서 동작)
) {
    public Land toEntity(){
        return Land.builder()
                .ownerId(UUID.fromString(ownerId.toString()))
                .ownerName(ownerName)
                .landName(landName)
                .landCategory(landCategory)
                .landArea(landArea)
                .landDescription(landDescription)
                .landAddress(landAddress)
                .landDetailAddress(landDetailAddress)
                .landPrice(landPrice)
                .landBuiltDate(landBuiltDate)
                .build();
    }
}
