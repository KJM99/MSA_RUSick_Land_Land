package com.example.land.service;

import com.example.land.domain.entity.Land;
import com.example.land.domain.repository.InterestLandRepository;
import com.example.land.domain.repository.LandRepository;
import com.example.land.domain.repository.SellLogRepository;
import com.example.land.dto.request.InterestLandRequest;
import com.example.land.dto.request.LandCreateRequest;
import com.example.land.dto.request.SellLogRequest;
import com.example.land.dto.response.InterestLandResponse;
import com.example.land.dto.response.SellLogResponse;
import com.example.land.global.utils.TokenInfo;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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
    @Autowired
    private InterestLandRepository interestLandRepository;
    @Autowired
    private SellLogRepository sellLogRepository;

    @Nested
    class 매물{
        @Test
        void 매물생성() {

            //given
            //request
            LocalDateTime time = LocalDateTime.of(
                    2020,2,20,10,30);
            LandCreateRequest landCreateRequest =
                    new LandCreateRequest(
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
            String randomUUID = UUID.randomUUID().toString();
            landService.addLandbyUserId(
                    landCreateRequest,
                    new TokenInfo(randomUUID,"dd", LocalDate.now()));
            //then
            assertNotNull(landRepository.findById(UUID.fromString(randomUUID)));


        }
        @Test
        void 이미존재(){
            //given
            //request
            LocalDateTime time = LocalDateTime.of(
                    2020,2,20,10,30);
            LandCreateRequest landCreateRequest =
                    new LandCreateRequest(
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
            String randomUUID = UUID.randomUUID().toString();
            landService.addLandbyUserId(
                    landCreateRequest,
                    new TokenInfo(randomUUID,"dd", LocalDate.now()));
            Optional<Land> optionalLand = landRepository.findById(UUID.fromString(randomUUID));
            //then
            assertFalse(optionalLand.isPresent());
        }
    }

    @Nested
    class 거래확정{
        @Test
        void 성공() {
            LocalDate time = LocalDate.of(2020, 2, 20);
            String ownerId = UUID.randomUUID().toString();
            Land land = Land.builder()
                    .id(UUID.randomUUID())
                    .ownerId(UUID.fromString(ownerId))
                    .ownerName("testNickname")
                    .landName("삼호진덕")
                    .landCategory(1)
                    .landArea("100")
                    .landDescription("어서오세요")
                    .landAddress("경기도 수원시")
                    .landDetailAddress("장안구 천천동")
                    .landPrice(100000l)
                    .landBuiltDate(LocalDateTime.now())
                    .landYN(true)
                    .build();
            Land savedLand = landRepository.save(land);
            String landId = savedLand.getId().toString();
            SellLogRequest sellLogRequest = new SellLogRequest(
                    landId,
                    LocalDateTime.now(),
                    100000l
            );
            TokenInfo tokenInfo = new TokenInfo(ownerId, "testNickname", time);
            //when
            landService.landConfirm(landId, sellLogRequest, tokenInfo);
            //then
            System.out.println(landRepository.findById(UUID.fromString(landId)).get().isLandYN());
            assertFalse(landRepository.findById(UUID.fromString(landId)).get().isLandYN());
        }
    }

    @Nested
    class 매물목록{
        @Test
        void 내매물목록조회(){
            //given
            LocalDate time = LocalDate.of(2020, 2, 20);
            String ownerId = UUID.randomUUID().toString();
            Land land = Land.builder()
                    .id(UUID.randomUUID())
                    .ownerId(UUID.fromString(ownerId))
                    .ownerName("testNickname")
                    .landName("삼호진덕")
                    .landCategory(1)
                    .landArea("100")
                    .landDescription("어서오세요")
                    .landAddress("경기도 수원시")
                    .landDetailAddress("장안구 천천동")
                    .landPrice(100000l)
                    .landBuiltDate(LocalDateTime.now())
                    .landYN(true)
                    .build();
            landRepository.save(land);
            //when
            TokenInfo tokenInfo = new TokenInfo(ownerId, "testNickname", time);
            //then
            assertEquals(1, landService.getLandsByUser(tokenInfo).size());
        }
        @Test
        void 매물목록조회(){
            //given
            LocalDate time = LocalDate.of(2020, 2, 20);
            String ownerId = UUID.randomUUID().toString();
            Land land = Land.builder()
                    .id(UUID.randomUUID())
                    .ownerId(UUID.fromString(ownerId))
                    .ownerName("testNickname")
                    .landName("삼호진덕")
                    .landCategory(1)
                    .landArea("100")
                    .landDescription("어서오세요")
                    .landAddress("경기도 수원시")
                    .landDetailAddress("장안구 천천동")
                    .landPrice(100000l)
                    .landBuiltDate(LocalDateTime.now())
                    .landYN(true)
                    .build();
            landRepository.save(land);
            //when
            //then
            assertEquals(1, landService.getLandsAll().size());
        }

        @Test
        // 매물 상세 정보
        void 매물상세정보(){
            //given
            LocalDate time = LocalDate.of(2020, 2, 20);
            String ownerId = UUID.randomUUID().toString();
            Land land = Land.builder()
                    .id(UUID.randomUUID())
                    .ownerId(UUID.fromString(ownerId))
                    .ownerName("testNickname")
                    .landName("삼호진덕")
                    .landCategory(1)
                    .landArea("100")
                    .landDescription("어서오세요")
                    .landAddress("경기도 수원시")
                    .landDetailAddress("장안구 천천동")
                    .landPrice(100000l)
                    .landBuiltDate(LocalDateTime.now())
                    .landYN(true)
                    .build();
            Land savedLand = landRepository.save(land);
            String landId = savedLand.getId().toString();
            //when
            //then
            assertEquals(landService.getLandDetail(landId).landName(), "삼호진덕");
        }
    }

    @Nested
    class 관심매물{
        // 관심 매물 등록
        @Test
        void 관심매물등록(){
            //given
            LocalDate time = LocalDate.of(2020, 2, 20);
            String ownerId = UUID.randomUUID().toString();
            String userId = UUID.randomUUID().toString();
            TokenInfo usertokenInfo = new TokenInfo(userId, "testNickname", time);
            Land land = Land.builder()
                    .id(UUID.randomUUID())
                    .ownerId(UUID.fromString(ownerId))
                    .ownerName("testNickname")
                    .landName("삼호진덕")
                    .landCategory(1)
                    .landArea("100")
                    .landDescription("어서오세요")
                    .landAddress("경기도 수원시")
                    .landDetailAddress("장안구 천천동")
                    .landPrice(100000l)
                    .landBuiltDate(LocalDateTime.now())
                    .landYN(true)
                    .build();
            Land savedLand = landRepository.save(land);
            String landId = savedLand.getId().toString();
            InterestLandRequest interestLandRequest = new InterestLandRequest(landId, usertokenInfo);

            //when
            landService.addOrDeleteInterestedLand(usertokenInfo, interestLandRequest);

            //then
            assertEquals(1,
                    interestLandRepository.findByUserId(UUID.fromString(userId)).size());
            assertEquals(savedLand.getId(),
                    interestLandRepository.findByLandAndUserId(savedLand, UUID.fromString(usertokenInfo.id()))
                            .getLand().getId());
        }

        @Test
        // 내 관심 매물 조회
        void 내관심매물조회(){
            //given
            LocalDate time = LocalDate.of(2020, 2, 20);
            String ownerId = UUID.randomUUID().toString();
            String userId = UUID.randomUUID().toString();
            TokenInfo usertokenInfo = new TokenInfo(userId, "testNickname", time);
            Land land = Land.builder()
                    .id(UUID.randomUUID())
                    .ownerId(UUID.fromString(ownerId))
                    .ownerName("testNickname")
                    .landName("삼호진덕")
                    .landCategory(1)
                    .landArea("100")
                    .landDescription("어서오세요")
                    .landAddress("경기도 수원시")
                    .landDetailAddress("장안구 천천동")
                    .landPrice(100000l)
                    .landBuiltDate(LocalDateTime.now())
                    .landYN(true)
                    .build();
            Land savedLand = landRepository.save(land);
            String landId = savedLand.getId().toString();

            InterestLandRequest interestLandRequest = new InterestLandRequest(landId, usertokenInfo);

            //when
            landService.addOrDeleteInterestedLand(usertokenInfo, interestLandRequest);
            List<InterestLandResponse> myLand = landService.getInterestLandByUser(usertokenInfo);
            System.out.println(myLand.get(0));

            //then
            assertEquals(1,
                    interestLandRepository.findByUserId(UUID.fromString(userId)).size());
            assertEquals(savedLand.getLandArea(), myLand.get(0).landArea());
        }
    }

    @Nested
    class 매물시세{
        @Test
        void 매물시세조회(){
            //given
            LocalDate time = LocalDate.of(2020, 2, 20);
            String ownerId = UUID.randomUUID().toString();
            Land land = Land.builder()
                    .id(UUID.randomUUID())
                    .ownerId(UUID.fromString(ownerId))
                    .ownerName("testNickname")
                    .landName("삼호진덕")
                    .landCategory(1)
                    .landArea("100")
                    .landDescription("어서오세요")
                    .landAddress("경기도 수원시")
                    .landDetailAddress("장안구 천천동")
                    .landPrice(100000l)
                    .landBuiltDate(LocalDateTime.now())
                    .landYN(true)
                    .build();
            Land savedLand = landRepository.save(land);
            TokenInfo tokenInfo = new TokenInfo(ownerId, "testNickname", time);
            SellLogRequest sellLogRequest = new SellLogRequest(
                    savedLand.getId().toString(),
                    LocalDateTime.now(),
                    100000l
            );
            landService.landConfirm(
                    savedLand.getId().toString(), sellLogRequest, tokenInfo);
            //when
            List<SellLogResponse> sellLog =
                landService.getLandPrice(savedLand.getId().toString());
            //then
            assertEquals(100000l, sellLog.get(0).price());
            assertEquals(savedLand.getId().toString(), sellLog.get(0).landId());
        }

        @Test
        void 내매물시세조회(){
            //given
            LocalDate time = LocalDate.of(2020, 2, 20);
            String ownerId = UUID.randomUUID().toString();
            Land land = Land.builder()
                    .id(UUID.randomUUID())
                    .ownerId(UUID.fromString(ownerId))
                    .ownerName("testNickname")
                    .landName("삼호진덕")
                    .landCategory(1)
                    .landArea("100")
                    .landDescription("어서오세요")
                    .landAddress("경기도 수원시")
                    .landDetailAddress("장안구 천천동")
                    .landPrice(100000l)
                    .landBuiltDate(LocalDateTime.now())
                    .landYN(true)
                    .build();
            Land savedLand = landRepository.save(land);
            TokenInfo tokenInfo = new TokenInfo(ownerId, "testNickname", time);
            SellLogRequest sellLogRequest = new SellLogRequest(
                    savedLand.getId().toString(),
                    LocalDateTime.now(),
                    100000l
            );
            landService.landConfirm(
                    savedLand.getId().toString(), sellLogRequest, tokenInfo);
            //when
            List<SellLogResponse> sellLog =
                    landService.getMyLandPrice(tokenInfo);
            //then
            assertEquals(100000l, sellLog.get(0).price());
            assertEquals(savedLand.getId().toString(), sellLog.get(0).landId());
        }
    }



}