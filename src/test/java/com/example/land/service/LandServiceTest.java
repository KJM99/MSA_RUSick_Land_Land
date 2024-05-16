package com.example.land.service;

import static org.junit.jupiter.api.Assertions.*;

import com.example.land.domain.repository.LandRepository;
import com.example.land.dto.response.LandToISaleResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LandServiceTest {

    // @Autowired
    // private LandRepository landRepository;
    // @Test
    // void getLandsByUserIdForISale() {
    //     List<UUID> list = new ArrayList<>();
    //     list.add(UUID.randomUUID());
    //     list.add(UUID.randomUUID());
    //     list.add(UUID.randomUUID());
    //     list.add(UUID.randomUUID());
    //
    //     landRepository.findByOwnerIdIn(list);
    //
    //     assert
    // }
}