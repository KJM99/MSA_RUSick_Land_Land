package com.example.land.service;

import com.example.land.dto.LandCreateRequest;

public interface LandService {
    void addLandbyUserId(LandCreateRequest req, Long id);
}
