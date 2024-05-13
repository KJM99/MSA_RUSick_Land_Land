package com.example.land.service;


import com.example.land.domain.entity.InterestLand;
import com.example.land.domain.entity.Land;
import com.example.land.domain.repository.InterestLandRepository;
import com.example.land.domain.repository.LandRepository;
import com.example.land.domain.repository.SellLogRepository;
import com.example.land.dto.request.InterestLandRequest;
import com.example.land.dto.request.LandCreateRequest;
import com.example.land.dto.request.SellLogRequest;
import com.example.land.dto.response.InterestLandResponse;
import com.example.land.dto.response.LandResponse;
import com.example.land.dto.response.LandToISaleResponse;
import com.example.land.dto.response.SellLogResponse;
import com.example.land.exception.ExistLandException;
import com.example.land.exception.NotEqualOwnerException;
import com.example.land.exception.NotExistInterestLand;
import com.example.land.exception.NotExistLandException;
import com.example.land.global.utils.TokenInfo;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    // 매물 생성
    @Override
    @Transactional
    public void addLandbyUserId(LandCreateRequest req, TokenInfo tokenInfo) {
        Land land = req.toEntity(tokenInfo);
        Optional<Land> lands = landRepository.findById(land.getId());
        if (lands.isPresent()) {
            throw new ExistLandException();
        }
        landRepository.save(land);
    }

    // 매물 삭제
    @Override
    public void deleteLand(String landid, TokenInfo tokenInfo) {
        Land land = landRepository.findById(UUID.fromString(landid)).orElseThrow(()
                -> new NotExistLandException());

        if(!tokenInfo.id().equals(String.valueOf(land.getOwnerId()))){
            throw new NotEqualOwnerException(String.valueOf(land.getOwnerId()));
        }
        landRepository.delete(land);
    }


    // 매물 거래 확정
    @Override
    @Transactional
    public void landConfirm(
            String landid, SellLogRequest req, TokenInfo tokenInfo
    ) {
        Land land = landRepository.findById(UUID.fromString(landid)).orElseThrow(()
                -> new NotExistLandException());

        if(!tokenInfo.id().equals(String.valueOf(land.getOwnerId()))){
            throw new NotEqualOwnerException(String.valueOf(land.getOwnerId()));
        }
        sellLogRepository.save(req.toEntity());
        land.setLandYN(false);
        landRepository.save(land);
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
            TokenInfo tokenInfo, InterestLandRequest req) {

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
                .findByLandId(UUID.fromString(landid))
                .stream()
                .map(SellLogResponse::from)
                .toList();
    }

    // 내가 등록한 매물 목록 조회
    @Override
    public List<LandResponse> getLandsByUser(TokenInfo tokenInfo) {
        return landRepository
                .findByOwnerId(UUID.fromString(tokenInfo.id()))
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
    public Map<UUID, Integer> getLandsByUserIdForISale(Set<UUID> idList) {
        Map<UUID, Integer> map = new HashMap<>();

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
}
