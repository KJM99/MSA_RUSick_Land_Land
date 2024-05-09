package com.example.land.global.domain.repository;

import com.example.land.global.domain.entity.Land;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface LandRepository
        extends JpaRepository<Land, UUID> {
    Optional<Land> findByOwnerId(UUID ownerId);
}
