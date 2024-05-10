package com.example.land.service;

import com.example.land.dto.request.LandCreateRequest;
import com.example.land.dto.request.SellLogRequest;
import com.example.land.dto.response.LandResponse;
import com.example.land.global.utils.TokenInfo;

import java.util.List;

public interface LandService {
    void addLandbyUserId(LandCreateRequest req, TokenInfo tokenInfo);

    void landConfirm(String landid,SellLogRequest req, TokenInfo tokenInfo);

    List<LandResponse> getLandsByUserId(TokenInfo tokenInfo);
}
