package com.example.land.global.domain.repository;

import com.example.land.global.domain.entity.InterestLand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterestLandRepository
        extends JpaRepository<InterestLand,Long> {
}
