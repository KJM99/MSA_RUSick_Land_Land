package com.example.land.service;

import com.example.land.dto.LandCreateRequest;
import com.example.land.global.domain.entity.Land;
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
    @Autowired
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
                "안녕하세요",
                "수원시",
                "장안구 천천동",
                100000l,
                testTime
        );

        //when
        landService.addLandbyUserId(req,1l);
        manager.flush();
        manager.clear();

        //then
        Land result = manager.createQuery(
                "SELECT l FROM Land l WHERE l.ownerName = :ownerName",
                        Land.class
                )
                .setParameter("ownerName", "hongbeom")
                .getSingleResult();

        assertNotNull(result);
        assertEquals("hongbeom", result.getOwnerName());
        assertEquals("apartment", result.getLandCategory());
        assertEquals(1, result.getLandArea());
        assertEquals("23평", result.getLandArea());
        assertEquals("자취", result.getLandDescription());
        assertEquals("수원시", result.getLandAddress());
        assertEquals("장안구 천천동", result.getLandDetailAddress());
        assertEquals(100000L, result.getLandPrice());
        assertEquals(testTime, result.getLandBuiltDate());
        assertTrue(result.isLandYN());
    }
}