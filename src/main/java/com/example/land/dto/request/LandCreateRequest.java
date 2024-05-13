package com.example.land.dto.request;


import com.example.land.domain.entity.Land;
import com.example.land.global.utils.TokenInfo;

import java.time.LocalDateTime;
import java.util.UUID;

public record LandCreateRequest(
        String landName,
        int landCategory,
        String landArea,
        String landDescription,
        String landAddress,
        String landDetailAddress,
        Long landPrice,
        String landBuiltDate

        // 매물 여부는 등록시 true로 변경(서비스에서 동작)
) {
    public Land toEntity(TokenInfo tokenInfo){
        return Land.builder()
                .id(UUID.randomUUID())
                .ownerId(UUID.fromString(tokenInfo.id()))
                .ownerName(tokenInfo.nickname())
                .landName(landName)
                .landCategory(landCategory)
                .landArea(landArea)
                .landDescription(landDescription)
                .landAddress(landAddress)
                .landDetailAddress(landDetailAddress)
                .landPrice(landPrice)
                .landBuiltDate(LocalDateTime.parse(landBuiltDate))
                .build();
    }
}
