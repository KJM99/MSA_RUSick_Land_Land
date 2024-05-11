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
}