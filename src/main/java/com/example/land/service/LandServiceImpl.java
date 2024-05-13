package com.example.land.service;


import com.example.land.domain.entity.Land;
import com.example.land.domain.entity.SellLog;
import com.example.land.domain.repository.LandRepository;
import com.example.land.domain.repository.SellLogRepository;
import com.example.land.dto.request.LandCreateRequest;
import com.example.land.dto.request.SellLogRequest;
import com.example.land.dto.response.LandResponse;
import com.example.land.dto.response.LandToISaleResponse;
import com.example.land.exception.ExistLandException;
import com.example.land.exception.NotEqualOwnerException;
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

    @Override
    @Transactional
    public void addLandbyUserId(LandCreateRequest req, TokenInfo tokenInfo) {

        Land land =
                Land.builder()
                        .ownerId(UUID.fromString(tokenInfo.id()))
                        .ownerName(tokenInfo.nickname())
                        .landName(req.landName())
                        .landCategory(req.landCategory())
                        .landArea(req.landArea())
                        .landDescription(req.landDescription())
                        .landAddress(req.landAddress())
                        .landDetailAddress(req.landDetailAddress())
                        .landPrice(req.landPrice())
                        .landBuiltDate(LocalDateTime.parse(req.landBuiltDate()))
                        .build();
        Optional<Land> lands = landRepository.findById(land.getId());
        if (lands.isPresent()) {
            throw new ExistLandException(land.getId());
        }
        landRepository.save(land);
    }

    @Override
    @Transactional
    public void landConfirm(String landid, SellLogRequest req, TokenInfo tokenInfo) {

        Optional<Land> confirmLand = landRepository.findById(UUID.fromString(landid));
        String ownerId = String.valueOf(confirmLand.get().getId());
        if(tokenInfo.id().equals(ownerId)){
            throw new NotEqualOwnerException(ownerId);
        }
        SellLog sellLog = req.toEntity();
        sellLogRepository.save(sellLog);

        String landId = String.valueOf(sellLog.getLand().getId());
        Land land = landRepository.findById(UUID.fromString(landId))
                .orElseThrow(() ->
                        new RuntimeException("해당 매물을 찾을 수 없습니다"));

        land.setLandYN(false);
        landRepository.save(land);
    }

    @Override
    public List<LandResponse> getLandsByUserId(UUID ownerId) {
        return landRepository.findByOwnerId(ownerId)
                .stream()
                .map(LandResponse::from)
                .toList();
    }

    @Override
    public Map<UUID, Integer> getLandsByUserIdForISale(Set<UUID> idList) {
        Map<UUID, Integer> map = new HashMap<>();

        // 집계 함수 사용
        List<LandToISaleResponse> byOwnerId = landRepository.findCountByOwnerId(idList);
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
