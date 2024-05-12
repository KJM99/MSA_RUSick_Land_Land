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
import com.example.land.exception.ExistLandException;
import com.example.land.exception.NotEqualOwnerException;
import com.example.land.global.utils.TokenInfo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LandServiceImpl implements LandService {

    private final LandRepository landRepository;
    private final SellLogRepository sellLogRepository;
    private final InterestLandRepository interestLandRepository;

    //매물 생성
    @Override
    @Transactional
    public void addLandbyUserId(LandCreateRequest req, TokenInfo tokenInfo) {
        Land land = req.toEntity(tokenInfo);
        Optional<Land> lands = landRepository.findById(land.getId());
        if (lands.isPresent()) {
            throw new ExistLandException(land.getId());
        }
        landRepository.save(land);
    }

    //매물 거래 확정
    @Override
    @Transactional
    public void landConfirm(
            String landid, SellLogRequest req, TokenInfo tokenInfo
    ) {
        Land land = landRepository.findById(UUID.fromString(landid)).orElseThrow(()
                -> new IllegalArgumentException("존재하지 않음"));

        if(!tokenInfo.id().equals(String.valueOf(land.getOwnerId()))){
            throw new NotEqualOwnerException(String.valueOf(land.getOwnerId()));
        }
        sellLogRepository.save(req.toEntity());
        land.setLandYN(false);
        landRepository.save(land);
    }

    //내가 등록한 매물 목록 조회
    @Override
    public List<LandResponse> getLandsByUser(TokenInfo tokenInfo) {
        return landRepository
                .findByOwnerId(UUID.fromString(tokenInfo.id()))
                .stream()
                .map(LandResponse::from)
                .toList();
    }

    //매물 목록 조회
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
                .orElseThrow(() -> new ExistLandException(UUID.fromString(landid)));
    }

    //관심 매물 등록
    @Override
    @Transactional
    public void addOrDeleteInterestedLand(
            TokenInfo tokenInfo, InterestLandRequest req) {
        Optional<Land> byId = landRepository.findById(UUID.fromString(req.landId()));
        Land land = byId.orElseThrow(() -> new IllegalArgumentException("뭔가 잘못됨"));
        InterestLand interestLand =
                interestLandRepository.findByLandAndUserId(land, UUID.fromString(req.tokenInfo().id()));
        if(interestLand != null){
            interestLandRepository.delete(interestLand);
        }else{
            interestLandRepository.save(req.toEntity());
        }
    }

    //내 관심 매물 조회
    /*
        interestLandRepository에서 userId로 조회한 후,
        조회한 land의 id를 다시 landRepository에서 조회하여
        InterestLandResponse로 변환하여 반환
     */
    @Override
    public List<InterestLandResponse> getInterestLandByUser(TokenInfo tokenInfo) {
        List<InterestLand> interestLandList
                = interestLandRepository.findByUserId(UUID.fromString(tokenInfo.id()));

        List<InterestLandResponse> interestLandResponseList = new ArrayList<>();

        if(interestLandList.isEmpty()) throw new IllegalArgumentException("리스트 없음");

        for (InterestLand interestLand : interestLandList) {
            interestLandResponseList.add(InterestLandResponse.from(interestLand.getLand()));
        }

        return interestLandResponseList;
    }



    //매물 시세조회



}
