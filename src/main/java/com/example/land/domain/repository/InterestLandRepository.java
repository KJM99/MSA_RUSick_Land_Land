package com.example.land.global.domain.repository;

import com.example.land.global.domain.entity.InterestLand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InterestLandRepository
        extends JpaRepository<InterestLand, UUID> {
}
