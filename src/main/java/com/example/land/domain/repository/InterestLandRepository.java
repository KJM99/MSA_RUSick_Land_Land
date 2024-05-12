package com.example.land.domain.repository;


import com.example.land.domain.entity.InterestLand;
import com.example.land.domain.entity.Land;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface InterestLandRepository
        extends JpaRepository<InterestLand, UUID> {
    InterestLand findByLandAndUserId(Land land, UUID userId);
    List<InterestLand> findByUserId(UUID userId);
}
