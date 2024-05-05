package com.example.land.global.domain.repository;

import com.example.land.global.domain.entity.Land;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LandRepository
        extends JpaRepository<Land,Long> {
}
