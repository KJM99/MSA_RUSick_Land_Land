package com.example.land.service;

import com.example.land.dto.request.LandCreateRequest;
import com.example.land.dto.request.SellLogRequest;
import com.example.land.dto.response.LandResponse;
import com.example.land.dto.response.LandToISaleResponse;
import com.example.land.global.utils.TokenInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface LandService {
    void addLandbyUserId(LandCreateRequest req, TokenInfo tokenInfo);

    void landConfirm(String landid,SellLogRequest req, TokenInfo tokenInfo);

    List<LandResponse> getLandsByUserId(UUID userId);

    Map<UUID, Integer> getLandsByUserIdForISale(Set<UUID> idList);
}
