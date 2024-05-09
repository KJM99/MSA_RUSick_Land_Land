package com.example.land.service;

import com.example.land.dto.LandCreateRequest;
import com.example.land.global.domain.entity.Land;
import com.example.land.global.domain.repository.LandRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class LandServiceImplTest {
    @Autowired
    private LandService landService;
    @Autowired
    private LandRepository landRepository;

    @Nested
    class 매물{
        @Test
        void 매물생성() {
            //given
            LocalDateTime time = LocalDateTime.of(2020,2,20,10,30);
            LandCreateRequest landCreateRequest =
                    new LandCreateRequest(
                            "cb158fd1-510c-4b01-a7ec-100a2ab5ed8f",
                            "hongbeom",
                            "삼호진덕",
                            1,
                            "100",
                            "어서오세요",
                            "경기도 수원시",
                            "장안구 천천동",
                            100000l,
                            time
                    );
            //when
            landService.addLandbyUserId(landCreateRequest);
            //then
            Optional<Land> land = landRepository.findByOwnerId(UUID.fromString(landCreateRequest.ownerId()));
            assertTrue(land.isPresent());
            assertTrue(land.get().isLandYN());
        }
    }



}