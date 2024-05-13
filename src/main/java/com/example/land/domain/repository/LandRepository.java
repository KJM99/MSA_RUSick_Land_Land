package com.example.land.domain.repository;


import com.example.land.domain.entity.Land;
import com.example.land.dto.response.LandToISaleResponse;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LandRepository
        extends JpaRepository<Land, UUID> {
    List<Land> findByOwnerId(UUID ownerId);


    @Query("select count(*) from Land where ownerId in :ownerIds group by ownerId")
    List<LandToISaleResponse> findCountByOwnerId(@Param("ownerIds") Set<UUID> ownerIds);
}
