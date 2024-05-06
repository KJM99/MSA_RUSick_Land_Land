package com.example.land.service;

import com.example.land.dto.LandCreateRequest;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static java.time.LocalTime.now;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class LandServiceImplTest {

    @Autowired
    private EntityManager manager;
    private LandService landService;

    @Test
    public void addLandbyUserId() {

        //give
        LocalDateTime testTime = LocalDateTime.of(2024, 5, 6, 15, 30);
        LandCreateRequest req = new LandCreateRequest(
                null,
                "hongbeom",
                "apartment",
                1,
                "23평",
                "자취",
                "수원시",
                "장안구 천천동",
                100000l,
                testTime,
                true
        );

        //when
        landService.addLandbyUserId(req,1l);
        manager.flush();
        manager.clear();

        //then

    }
}