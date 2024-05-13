package com.example.land.global.utils;

import com.example.land.global.config.ServerConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtUtilTest {

    @Autowired
    private ServerConfig serverConfig;

    @Test
    void parseToken() {
        String secret = "asjdkfnof4241085931nklasf1n1032nlkdsfmi1m2k2";
        assertEquals(secret,serverConfig.getSecret());
        System.out.println(serverConfig.getSecret());
    }

//    class JwtUtilTest {
//        private final ServerConfig serverConfig = new ServerConfig("asjdkfnof4241085931nklasf1n1032nlkdsfmi1m2k2", 600000L);
//        private final JwtUtil jwtUtil = new JwtUtil(serverConfig);
//        @Test
//        void createToken() {
//            User user = User.builder()
//                    .id(UUID.randomUUID())
//                    .email("a@a.com")
//                    .nickname("aa")
//                    .gender("남")
//                    .birthDay(LocalDate.now())
//                    .build();
//
//            String token = jwtUtil.createToken(user);
//            System.out.println(token);
//            assertNotNull(token);
//
//        }
}