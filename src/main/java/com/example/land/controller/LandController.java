package com.example.land.controller;

import com.example.land.dto.request.InterestLandRequest;
import com.example.land.dto.request.LandCreateRequest;
import com.example.land.dto.request.SellLogRequest;
import com.example.land.dto.response.InterestLandResponse;
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
            @RequestBody LandCreateRequest req,
            @AuthenticationPrincipal TokenInfo tokenInfo
            ){
        landService.addLandbyUserId(req,tokenInfo);
    }

    // 거래 확정
    @PutMapping("{landid}")
    public void landConfirm(
            @PathVariable String landid,
            @RequestBody SellLogRequest req,
            @AuthenticationPrincipal TokenInfo tokenInfo){
        landService.landConfirm(landid,req,tokenInfo);
    }

    // 내가 등록한 매물 목록 조회
    @GetMapping("/mylands")
    public List<LandResponse> getLandsByUserId(
            @AuthenticationPrincipal TokenInfo tokenInfo
    ){
        return landService.getLandsByUser(tokenInfo);
    }

    // 매물 목록 조회(기준은 프론트에서 필터로 구현하기)
    @GetMapping
    public List<LandResponse> getLandsAll(){
        return landService.getLandsAll();
    }

    // 관심 매물 등록
    @PostMapping("/interests")
    public void addOrLandInterest(
            @AuthenticationPrincipal TokenInfo tokenInfo,
            @RequestBody InterestLandRequest interestLandRequest
            ){
        landService.addOrDeleteInterestedLand(tokenInfo,interestLandRequest);

    }

    // 관심 매물 조회
    @GetMapping("/interests")
    public List<InterestLandResponse> getLandInterests(
            @AuthenticationPrincipal TokenInfo tokenInfo
    ){
        return landService.getInterestLandByUser(tokenInfo);
    }

    // 매물 상세 정보



    // 매물 시세조회














}
