package com.example.land.controller;

import com.example.land.dto.request.InterestLandRequest;
import com.example.land.dto.request.LandCreateRequest;
import com.example.land.dto.request.SellLogRequest;
import com.example.land.dto.response.InterestLandResponse;
import com.example.land.dto.response.LandResponse;
import com.example.land.dto.response.SellLogResponse;
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
    @PostMapping("/{landId}")
    public void deleteLand(
            @PathVariable String landId,
            @AuthenticationPrincipal TokenInfo tokenInfo
    ){
        landService.deleteLand(landId,tokenInfo);
    }

    // 거래 확정
    @PutMapping("/{landId}")
    public void landConfirm(
            @PathVariable String landId,
            @RequestBody SellLogRequest req,
            @AuthenticationPrincipal TokenInfo tokenInfo){
        landService.landConfirm(landId,req,tokenInfo);
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
        List<LandResponse> landResponses = landService.getLandsAll();
        return landResponses;
    }

    // 관심 매물 등록 및 삭제
    @PostMapping("{landId}/interests")
    public void addOrLandInterest(
            @PathVariable String landId,
            @AuthenticationPrincipal TokenInfo tokenInfo
            ){
        InterestLandRequest interestLandRequest = new InterestLandRequest(landId,tokenInfo);
        landService.addOrDeleteInterestedLand(interestLandRequest);

    }

    @GetMapping("{landId}/interests")
    public Boolean getInterest(@PathVariable("landId") String landId, @AuthenticationPrincipal TokenInfo tokenInfo){
        return landService.getInterest(landId, tokenInfo);
    }


    // 관심 매물 조회
    @GetMapping("/interests")
    public List<InterestLandResponse> getLandInterests(
            @AuthenticationPrincipal TokenInfo tokenInfo
    ){
        return landService.getInterestLandByUser(tokenInfo);

    }

    @PostMapping("/owner/landCount")
    public Map<UUID, Long> getLandsByUserIdForISale(@RequestBody List<UUID> idList){
        return landService.getLandsByUserIdForISale(idList);
    }

    // 매물 상세 정보
    @GetMapping("/{landId}")
    public LandResponse getLandDetail(
            @PathVariable String landId
    ){
        return landService.getLandDetail(landId);
    }

    // 매물 시세조회
    @GetMapping("/price/{landId}")
    public List<SellLogResponse> getLandPrice(
            @PathVariable String landId
    ){
        return landService.getLandPrice(landId);
    }

    // 내가 등록한 매물 시세 조회
    @GetMapping("/mylands/price")
    public List<SellLogResponse> getMyLandPrice(
            @AuthenticationPrincipal TokenInfo tokenInfo
    ){
        return landService.getMyLandPrice(tokenInfo);
    }
}
