package com.example.land.service;


import com.example.land.api.ApiAuth;
import com.example.land.domain.entity.InterestLand;
import com.example.land.domain.entity.Land;
import com.example.land.domain.entity.SellLog;
import com.example.land.domain.repository.InterestLandRepository;
import com.example.land.domain.repository.LandRepository;
import com.example.land.domain.repository.SellLogRepository;
import com.example.land.dto.request.InterestLandRequest;
import com.example.land.dto.request.LandCreateRequest;
import com.example.land.dto.request.SellLogRequest;
import com.example.land.dto.response.*;
import com.example.land.exception.ExistLandException;
import com.example.land.exception.NotEqualOwnerException;
import com.example.land.exception.NotExistInterestLand;
import com.example.land.exception.NotExistLandException;
import com.example.land.global.utils.TokenInfo;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LandServiceImpl implements LandService {

    private final LandRepository landRepository;
    private final SellLogRepository sellLogRepository;
    private final InterestLandRepository interestLandRepository;
    private final ApiAuth apiAuth;

    // 매물 생성
    @Override
    @Transactional
    public void addLandbyUserId(LandCreateRequest req, TokenInfo tokenInfo) {
        Land land = landRepository.findOneByLandAddressAndLandDetailAddress(req.landAddress(), req.landDetailAddress());
        if (land != null) {
            if (UUID.fromString(tokenInfo.id()) == land.getOwnerId()) {
                land.setLandYN(true);
            } else {
                throw new NotEqualOwnerException(tokenInfo.id());
            }
        } else {
            landRepository.save(req.toEntity(tokenInfo));
        }
//        Land land = req.toEntity(tokenInfo);
//        Optional<Land> lands = landRepository.findById(land.getId());
//        if (lands.isPresent()) {
//            throw new ExistLandException();
//        }
//        landRepository.save(land);
    }

    // 매물 삭제
    @Override
    @Transactional
    public void deleteLand(String landid, TokenInfo tokenInfo) {
        Land land = landRepository.findById(UUID.fromString(landid)).orElseThrow(()
                -> new NotExistLandException());

        if(!tokenInfo.id().equals(String.valueOf(land.getOwnerId()))){
            throw new NotEqualOwnerException(String.valueOf(land.getOwnerId()));
        }
        land.setLandYN(false);
    }


    // 매물 거래 확정
    @Override
    @Transactional
    public void landConfirm(
            String landid, SellLogRequest req, TokenInfo tokenInfo
    ) {
        UserInfoResponse response = apiAuth.getUserIdByEmail(req.buyerEmail());
        if(response == null){
            throw new RuntimeException("구매자의 이메일을 확인해주세요");
        }
        Land land = landRepository.findById(UUID.fromString(landid)).orElseThrow(()
                -> new NotExistLandException());

        if(!tokenInfo.id().equals(String.valueOf(land.getOwnerId()))){
            throw new NotEqualOwnerException(String.valueOf(land.getOwnerId()));
        }
        SellLog sellLog = req.toEntity(landid, tokenInfo);
        sellLogRepository.save(sellLog);
//        land.setLandYN(false);
        land.setOwnerId(response.userId());
        land.setOwnerName(response.userName());
    }

    // 매물 목록 조회
    @Override
    public List<LandResponse> getLandsAll() {
        return landRepository
                .findAll()
                .stream()
                .map(LandResponse::from)
                .toList();
    }

    // 매물 상세 정보
    @Override
    public LandResponse getLandDetail(String landid) {
        return landRepository.findById(UUID.fromString(landid))
                .map(LandResponse::from)
                .orElseThrow(() -> new NotExistLandException());
    }

    // 관심 매물 등록
    @Override
    @Transactional
    public void addOrDeleteInterestedLand(
            InterestLandRequest req) {
        Optional<Land> byId = landRepository.findById(UUID.fromString(req.landId()));
        Land land = byId.orElseThrow(() -> new NotExistLandException());

        InterestLand interestLand =
                interestLandRepository.findByLandAndUserid(land, UUID.fromString(req.tokenInfo().id()));
        if(interestLand != null){
            interestLandRepository.delete(interestLand);
        }else{
            interestLandRepository.save(req.toEntity());
        }

    }

    // 내 관심 매물 조회
    @Override
    public List<InterestLandResponse> getInterestLandByUser(TokenInfo tokenInfo) {
        List<InterestLand> interestLandList
                = interestLandRepository.findAllByUserId(UUID.fromString(tokenInfo.id()));

        List<InterestLandResponse> interestLandResponseList = new ArrayList<>();

        if(interestLandList.isEmpty()) throw new NotExistInterestLand();

        for (InterestLand interestLand : interestLandList) {
            interestLandResponseList.add(InterestLandResponse.from(interestLand));
        }
        return interestLandResponseList;
    }

    // 매물 시세조회
    @Override
    public List<SellLogResponse> getLandPrice(String landid) {
        return sellLogRepository
                .findByLandIdOrderBySellLogDateAsc(UUID.fromString(landid))
                .stream()
                .map(SellLogResponse::from)
                .toList();
    }

    // 내가 등록한 매물 목록 조회
    @Override
    public List<LandResponse> getLandsByUser(TokenInfo tokenInfo) {
        return landRepository
                .findByOwnerIdAndLandYNIsTrue(UUID.fromString(tokenInfo.id()))
                .stream()
                .map(LandResponse::from)
                .toList();
    }

    // 내가 등록한 매물 시세 조회
    @Override
    public List<SellLogResponse> getMyLandPrice(TokenInfo tokenInfo) {
        List<Land> lands = landRepository.findByOwnerId(UUID.fromString(tokenInfo.id()));
        List<SellLogResponse> sellLogResponses = new ArrayList<>();
        for (Land land : lands) {
            List<SellLogResponse> sellLogResponse = sellLogRepository.findByLandId(land.getId())
                    .stream()
                    .map(SellLogResponse::from)
                    .toList();
            sellLogResponses.addAll(sellLogResponse);
        }
        return sellLogResponses;
    }

    @Override
    public Map<UUID, Long> getLandsByUserIdForISale(List<UUID> idList) {
        Map<UUID, Long> map = new HashMap<>();

        // 집계 함수 사용
        List<LandToISaleResponse> byOwnerId = landRepository.findByOwnerIdIn(idList);
        for(LandToISaleResponse item : byOwnerId) {
            map.put(item.ownerId(), item.count());
        }

        // 리스트 사용
        // for (UUID id : idList) {
        //     List<Land> byOwnerId = landRepository.findByOwnerId(id);
        //     map.put(id, byOwnerId.size());
        // }

        return map;
    }

    @Override
    public Boolean getInterest(String landId, TokenInfo tokenInfo) {
        Optional<Land> byId = landRepository.findById(UUID.fromString(landId));
        Land land = byId.orElseThrow(() -> new IllegalArgumentException("landId is not exist"));
        InterestLand byLandAndUserId = interestLandRepository.findByLandAndUserid(land, UUID.fromString(tokenInfo.id()));

        return byLandAndUserId != null;
    }
}
