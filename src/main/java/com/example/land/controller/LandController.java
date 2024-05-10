package com.example.land.controller;

import com.example.land.dto.request.LandCreateRequest;
import com.example.land.dto.request.SellLogRequest;
import com.example.land.dto.response.LandResponse;
import com.example.land.global.utils.TokenInfo;
import com.example.land.service.LandService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/lands")
public class LandController {

    private final LandService landService;

    // 매물 등록
    @PostMapping
    public void addLandbyUserId(
            // 추후 유저 토큰으로 유저정보 불러오기 필요할 듯?
            @RequestBody LandCreateRequest req,
            @AuthenticationPrincipal TokenInfo tokenInfo
            ){
        landService.addLandbyUserId(req,tokenInfo);
    }

    // 매물 구매확정 (매물 구매 시 landYN => no, sellLog에 등록)
    // 구매 확정란에 가격, 거래날짜 now()) 입력
    // 현재 유저의 아이디와 매물의 ownerId가 일치하는지 확인 필요(토큰)

    @PutMapping("{landid}")
    public void landConfirm(
            @PathVariable String landid,
            @RequestBody SellLogRequest req,
            @AuthenticationPrincipal TokenInfo tokenInfo){
        landService.landConfirm(landid,req,tokenInfo);
    }

    // 내가 등록한 매물 목록 조회
    @GetMapping
    public List<LandResponse> getLandsByUserId(
            @AuthenticationPrincipal TokenInfo tokenInfo
    ){
        return landService.getLandsByUserId(tokenInfo);
    }

    // 매물 상세 정보


    // 매물 시세조회








}
