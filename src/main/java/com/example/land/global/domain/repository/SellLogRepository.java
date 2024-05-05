package com.example.land.global.domain.repository;

import com.example.land.global.domain.entity.SellLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellLogRepository
        extends JpaRepository<SellLog,Long> {
}
