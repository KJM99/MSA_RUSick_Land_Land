package com.example.land.global.domain.repository;

import com.example.land.global.domain.entity.SellLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SellLogRepository
        extends JpaRepository<SellLog, UUID> {
}
