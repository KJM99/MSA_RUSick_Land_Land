package com.example.land.controller;

import com.example.land.dto.request.InterestLandRequest;
import com.example.land.dto.request.LandCreateRequest;
import com.example.land.dto.request.SellLogRequest;
import com.example.land.dto.response.InterestLandResponse;
import com.example.land.dto.response.LandResponse;
import com.example.land.dto.response.SellLogResponse;
import com.example.land.dto.response.LandToISaleResponse;
import com.example.land.global.utils.TokenInfo;
import com.example.land.service.LandService;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
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

    // 매물 삭제
    @DeleteMapping("/{landid}")
    public void deleteLand(
            @PathVariable String landid,
            @AuthenticationPrincipal TokenInfo tokenInfo
    ){
        landService.deleteLand(landid,tokenInfo);
    }

    // 거래 확정
    @PutMapping("/{landid}")
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

    // 관심 매물 등록 및 삭제
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

    @GetMapping("/owner/landCount")
    public Map<UUID, Integer> getLandsByUserIdForISale(Set<UUID> idList){
        return landService.getLandsByUserIdForISale(idList);
    }

    // 매물 상세 정보
    @GetMapping("/{landid}")
    public LandResponse getLandDetail(
            @PathVariable String landid
    ){
        return landService.getLandDetail(landid);
    }

    // 매물 시세조회
    @GetMapping("/price/{landid}")
    public List<SellLogResponse> getLandPrice(
            @PathVariable String landid
    ){
        return landService.getLandPrice(landid);
    }

    // 내가 등록한 매물 시세 조회
    @GetMapping("/mylands/price")
    public List<SellLogResponse> getMyLandPrice(
            @AuthenticationPrincipal TokenInfo tokenInfo
    ){
        return landService.getMyLandPrice(tokenInfo);
    }



















}
