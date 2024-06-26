package com.example.land.service;


import com.example.land.dto.LandCreateRequest;
import com.example.land.dto.SellLogRequest;
import com.example.land.exception.ExistLandException;
import com.example.land.global.domain.entity.Land;
import com.example.land.global.domain.entity.SellLog;
import com.example.land.global.domain.repository.LandRepository;
import com.example.land.global.domain.repository.SellLogRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LandServiceImpl implements LandService {

    private final LandRepository landRepository;
    private final SellLogRepository sellLogRepository;

    @Override
    @Transactional
    public void addLandbyUserId(LandCreateRequest req) {
        Land land = req.toEntity();
        Optional<Land> landOptional = landRepository.findByOwnerId(land.getOwnerId());
        if (landOptional.isPresent()) {
            throw new ExistLandException(land.getId());
        }
        land.setLandYN(true);
        landRepository.save(land);
    }

    @Override
    @Transactional
    public void landConfirm(SellLogRequest req) {

        // 거래 확정
        // 현재 유저의 아이디와 매물의 ownerId가 일치하는지 확인 필요(토큰)

        SellLog sellLog = req.toEntity();
        sellLogRepository.save(sellLog);

        /*
            sellLog에 저장되는 landid에 해당하는 land를 불러와서
            LandYN를 false로 수정한 후
            다시 db에 저장 => putMapping
         */
        String landId = String.valueOf(sellLog.getId());
        Land land = landRepository.findById(UUID.fromString(landId))
                .orElseThrow(() ->
                        new RuntimeException("해당 매물을 찾을 수 없습니다"));

        land.setLandYN(false);
        landRepository.save(land);

    }
}
