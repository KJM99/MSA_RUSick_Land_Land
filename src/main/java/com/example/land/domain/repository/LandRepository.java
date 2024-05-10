package com.example.land.domain.repository;


import com.example.land.domain.entity.Land;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LandRepository
        extends JpaRepository<Land, UUID> {
    List<Land> findByOwnerId(UUID ownerId);
}
