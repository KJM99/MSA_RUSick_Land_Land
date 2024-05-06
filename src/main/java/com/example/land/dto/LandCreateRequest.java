package com.example.land.dto;

import com.example.land.global.domain.entity.Land;

import java.time.LocalDateTime;

public record LandCreateRequest(
        // ownerId는 컨트롤러의 매개변수로 따로?
        Long ownerId,

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
    public Land toEntity(Long ownerId){
        return Land.builder()
                .ownerId(ownerId)
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
