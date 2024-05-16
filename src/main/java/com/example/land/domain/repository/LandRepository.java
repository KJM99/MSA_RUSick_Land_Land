package com.example.land.domain.repository;


import com.example.land.domain.entity.Land;
import com.example.land.dto.response.LandToISaleResponse;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LandRepository
        extends JpaRepository<Land, UUID> {

    List<Land> findByOwnerId(UUID ownerId);
    @Query("SELECT new com.example.land.dto.response.LandToISaleResponse(l.ownerId, count(*))  " +
            "FROM Land l " +
            "WHERE l.ownerId IN :list " +
            "GROUP BY l.ownerId")
    List<LandToISaleResponse> findByOwnerIdIn(@Param("list") List<UUID> list);
    List<Land> findByOwnerIdAndLandYNIsTrue(UUID ownerId);
    Land findOneByLandAddressAndLandDetailAddress(String landAddress, String landDetailAddress);
}
