package com.example.land.service;

import com.example.land.dto.request.InterestLandRequest;
import com.example.land.dto.request.LandCreateRequest;
import com.example.land.dto.request.SellLogRequest;
import com.example.land.dto.response.InterestLandResponse;
import com.example.land.dto.response.LandResponse;
import com.example.land.dto.response.LandToISaleResponse;
import com.example.land.dto.response.SellLogResponse;
import com.example.land.global.utils.TokenInfo;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface LandService {
    void addLandbyUserId(LandCreateRequest req, TokenInfo tokenInfo);

    void landConfirm(String landid,SellLogRequest req, TokenInfo tokenInfo);

    List<LandResponse> getLandsByUser(TokenInfo tokenInfo);

    List<LandResponse> getLandsAll();

    void addOrDeleteInterestedLand(InterestLandRequest req);

    List<InterestLandResponse> getInterestLandByUser(TokenInfo tokenInfo);

    LandResponse getLandDetail(String landid);

    List<SellLogResponse> getLandPrice(String landid);

    void deleteLand(String landid, TokenInfo tokenInfo);

    List<SellLogResponse> getMyLandPrice(TokenInfo tokenInfo);

    Map<UUID, Long> getLandsByUserIdForISale(List<UUID> idList);

    Boolean getInterest(String landId, TokenInfo tokenInfo);
}
