package com.example.land.service;

import com.example.land.domain.entity.InterestLand;
import com.example.land.domain.entity.Land;
import com.example.land.domain.entity.SellLog;
import com.example.land.domain.repository.InterestLandRepository;
import com.example.land.domain.repository.LandRepository;
import com.example.land.domain.repository.SellLogRepository;
import com.example.land.dto.request.InterestLandRequest;
import com.example.land.dto.request.LandCreateRequest;
import com.example.land.dto.request.SellLogRequest;
import com.example.land.dto.response.InterestLandResponse;
import com.example.land.dto.response.SellLogResponse;
import com.example.land.global.utils.JwtUtil;
import com.example.land.global.utils.TokenInfo;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Map;
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
    private EntityManager entityManager;
    @Autowired
    private SellLogRepository sellLogRepository;
    @Autowired
    private JwtUtil jwtUtil;

    @Nested
    class 매물{
        @Test
        void 매물생성() {
            //given
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
                            time.toString()
                    );
            //when
            String userId = UUID.randomUUID().toString();
            TokenInfo tokenInfo = new TokenInfo(userId,"dd", LocalDate.now());
            landService.addLandbyUserId(
                    landCreateRequest, tokenInfo);
            //then
            assertEquals("삼호진덕",landRepository
                    .findByOwnerId(UUID.fromString(userId)).get(0).getLandName());
            assertNotNull(landRepository.findById(UUID.fromString(userId)));
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
                            time.toString()
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

        @Test
        void 매물삭제(){
            //given
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
                            time.toString()
                    );
            //when
            String userId = UUID.randomUUID().toString();
            TokenInfo tokenInfo = new TokenInfo(userId,"dd", LocalDate.now());
            landService.addLandbyUserId(
                    landCreateRequest, tokenInfo);
            landService.deleteLand(
                    landRepository.findByOwnerId(UUID.fromString(userId)).get(0).getId().toString(),
                    tokenInfo);
            //then
            assertTrue(landRepository.findByOwnerId(UUID.fromString(userId)).isEmpty());
        }
    }

    @Nested
    class 거래확정{
        @Test
        void 성공() {
            LocalDate time = LocalDate.of(2020, 2, 20);
            String sellerId = UUID.randomUUID().toString();
            Land land = Land.builder()
                    .id(UUID.randomUUID())
                    .ownerId(UUID.fromString(sellerId))
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
                    "test1@test.com",
                    100000000L
            );
            TokenInfo tokenInfo = new TokenInfo(sellerId, "testNickname", time);
            int oldLen = sellLogRepository.findAll().size();
            //when
            landService.landConfirm(landId, sellLogRequest, tokenInfo);
            entityManager.flush();
            entityManager.clear();
            //then
            assertFalse(landRepository.findById(UUID.fromString(landId)).get().getLandYN());
            assertNotEquals(sellerId, landRepository.findById(UUID.fromString(landId)).get().getOwnerId());
            assertNotEquals("testNickname", landRepository.findById(UUID.fromString(landId)).get().getOwnerName());
            assertEquals("test1", landRepository.findById(UUID.fromString(landId)).get().getOwnerName());
            assertEquals(oldLen + 1, sellLogRepository.findAll().size());
        }
        @Test
        void 실패(){
            LocalDate time = LocalDate.of(2020, 2, 20);
            String sellerId = UUID.randomUUID().toString();
            Land land = Land.builder()
                    .id(UUID.randomUUID())
                    .ownerId(UUID.fromString(sellerId))
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
                    "test123456@test.com",
                    100000000L
            );
            TokenInfo tokenInfo = new TokenInfo(sellerId, "testNickname", time);
            int oldLen = sellLogRepository.findAll().size();
            //when & then
            assertThrows(RuntimeException.class, () -> landService.landConfirm(landId, sellLogRequest, tokenInfo));
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
            assertEquals("삼호진덕", landService.getLandsByUser(tokenInfo).get(0).landName());
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
            assertEquals("삼호진덕", landService.getLandsAll().get(0).landName());
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
            System.out.println(savedLand.getLandName());
            InterestLandRequest interestLandRequest
                    =  new InterestLandRequest(landId);

            //when
            //then
            landService.addOrDeleteInterestedLand(usertokenInfo, interestLandRequest);
            entityManager.flush();
            entityManager.clear();
            InterestLand interestLand =
                    interestLandRepository.findByLandAndUserid(savedLand, UUID.fromString(userId));

            assertEquals(1, interestLandRepository.findAll().size());
            assertEquals(savedLand.getId(), interestLand.getLand().getId());

            landService.addOrDeleteInterestedLand(usertokenInfo, interestLandRequest);
            assertEquals(0, interestLandRepository.findAll().size());
        }


        // 내 관심 매물 조회
        @Test
        @Transactional
        void 내관심매물조회(){
            //given
            LocalDate birthday = LocalDate.of(1998, 2, 16);
            String ownerId = UUID.randomUUID().toString();
            String userId = UUID.randomUUID().toString();
            TokenInfo usertokenInfo = new TokenInfo(userId, "testNickname", birthday);
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
            InterestLandRequest interestLandRequest =
                    new InterestLandRequest(landId);
            //when
            landService.addOrDeleteInterestedLand(usertokenInfo, interestLandRequest);
            entityManager.flush();
            entityManager.clear();
            List<InterestLandResponse> myLand =
                    landService.getInterestLandByUser(usertokenInfo);
            //then
            assertEquals(1,
                    interestLandRepository.findAllByUserId(UUID.fromString(userId)).size());
            assertEquals(savedLand.getId().toString(), myLand.get(0).id());
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
    @Test
    void 테스트(){
        List<SellLogResponse> list = landService.getLandPrice("c50b4918-dc5d-4921-b3e6-1d3da8d5f581");
        List<SellLog> list1 = sellLogRepository.findAll();
        System.out.println(list1.get(0).getLand().getId());
        assertEquals(1, list.size());
    }
    @Test
    @Transactional
    void 테스트_2(){
        UUID owner1 = UUID.randomUUID();
        UUID owner2 = UUID.randomUUID();
        UUID owner3 = UUID.randomUUID();
        UUID owner4 = UUID.randomUUID();
        UUID owner5 = UUID.randomUUID();
        UUID owner6 = UUID.randomUUID();
        Land land1 = Land.builder().ownerId(owner1).landAddress("t").landArea("t").landBuiltDate(LocalDateTime.now()).landCategory(1).landName("t").landPrice(10000L).landYN(false).ownerName("t").build();
        Land land2 = Land.builder().ownerId(owner2).landAddress("t").landArea("t").landBuiltDate(LocalDateTime.now()).landCategory(1).landName("t").landPrice(10000L).landYN(false).ownerName("t").build();
        Land land3 = Land.builder().ownerId(owner3).landAddress("t").landArea("t").landBuiltDate(LocalDateTime.now()).landCategory(1).landName("t").landPrice(10000L).landYN(false).ownerName("t").build();
        Land land4 = Land.builder().ownerId(owner4).landAddress("t").landArea("t").landBuiltDate(LocalDateTime.now()).landCategory(1).landName("t").landPrice(10000L).landYN(false).ownerName("t").build();
        Land land5 = Land.builder().ownerId(owner5).landAddress("t").landArea("t").landBuiltDate(LocalDateTime.now()).landCategory(1).landName("t").landPrice(10000L).landYN(false).ownerName("t").build();
        Land land6 = Land.builder().ownerId(owner6).landAddress("t").landArea("t").landBuiltDate(LocalDateTime.now()).landCategory(1).landName("t").landPrice(10000L).landYN(false).ownerName("t").build();
        Land land7 = Land.builder().ownerId(owner1).landAddress("t").landArea("t").landBuiltDate(LocalDateTime.now()).landCategory(1).landName("t").landPrice(10000L).landYN(false).ownerName("t").build();
        Land land8 = Land.builder().ownerId(owner2).landAddress("t").landArea("t").landBuiltDate(LocalDateTime.now()).landCategory(1).landName("t").landPrice(10000L).landYN(false).ownerName("t").build();
        Land land9 = Land.builder().ownerId(owner2).landAddress("t").landArea("t").landBuiltDate(LocalDateTime.now()).landCategory(1).landName("t").landPrice(10000L).landYN(false).ownerName("t").build();
        Land land10 = Land.builder().ownerId(owner4).landAddress("t").landArea("t").landBuiltDate(LocalDateTime.now()).landCategory(1).landName("t").landPrice(10000L).landYN(false).ownerName("t").build();

        landRepository.save(land1);
        landRepository.save(land2);
        landRepository.save(land3);
        landRepository.save(land4);
        landRepository.save(land5);
        landRepository.save(land6);
        landRepository.save(land7);
        landRepository.save(land8);
        landRepository.save(land9);
        landRepository.save(land10);

        List<UUID> set = new ArrayList<>(

        );
        set.add(owner1);
        set.add(owner2);
        set.add(owner3);
        set.add(UUID.randomUUID());
        set.add(UUID.randomUUID());
        Map<UUID, Long> map = landService.getLandsByUserIdForISale(set);
        System.out.println(map);
    }

    @Test
    void 판매로그_생성(){
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJpZCI6ImZmOWEwMmY1LTIwMTMtNDExMS04MmY3LTUxMjk2NTBlYjhhMCIsIm5pY2tuYW1lIjoidGVzdDMiLCJiaXJ0aERheSI6IjIwMjQtMDUtMTQiLCJleHAiOjE3MTYyNjMxNzN9.UT5NhS1cURx3ffBv2QfwIMwCtZFSP5Q7n8TAIJttyU0;";
        TokenInfo tokenInfo = jwtUtil.parseToken(token);
        Land land = landRepository.findByOwnerId(UUID.fromString(tokenInfo.id())).get(0);
        SellLogRequest request = new SellLogRequest("test3@test.com", land.getLandPrice());
        landService.landConfirm(String.valueOf(land.getId()), request, tokenInfo);
    }
}