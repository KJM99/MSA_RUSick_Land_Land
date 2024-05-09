package com.example.land.controller;

import com.example.land.dto.LandCreateRequest;
import com.example.land.dto.SellLogRequest;
import com.example.land.service.LandService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/lands")
public class LandController {

    private final LandService landService;

    // 매물 등록
    @PostMapping
    public void addLandbyUserId(
            // 추후 유저 토큰으로 유저정보 불러오기 필요
            @RequestBody LandCreateRequest req){
        landService.addLandbyUserId(req);
    }

    // 매물 구매확정 (매물 구매 시 landYN => no, sellLog에 등록)
    // 구매 확정란에 가격, 거래날짜 now()) 입력
    @PutMapping
    public void landConfirm(
            @RequestBody SellLogRequest req){
        landService.landConfirm(req);
    }




}
