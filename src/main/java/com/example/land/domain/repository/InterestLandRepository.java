package com.example.land.domain.repository;


import com.example.land.domain.entity.InterestLand;
import com.example.land.domain.entity.Land;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface InterestLandRepository
        extends JpaRepository<InterestLand, UUID> {
    @Query("select i from InterestLand i where i.land = :land and i.userId = :userId")
    InterestLand findByLandAndUserid(@Param("land")Land land, @Param("userId") UUID userId);

    List<InterestLand> findAllByUserId(UUID userId);
}
