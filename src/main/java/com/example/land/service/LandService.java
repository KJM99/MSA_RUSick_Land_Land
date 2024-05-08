package com.example.land.service;

import com.example.land.dto.LandCreateRequest;
import com.example.land.dto.SellLogRequest;

public interface LandService {
    void addLandbyUserId(LandCreateRequest req);

    void landConfirm(SellLogRequest req);
}
