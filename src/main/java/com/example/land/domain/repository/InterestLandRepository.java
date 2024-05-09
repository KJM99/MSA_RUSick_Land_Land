package com.example.land.domain.repository;


import com.example.land.domain.entity.InterestLand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InterestLandRepository
        extends JpaRepository<InterestLand, UUID> {
}
