package com.example.land.domain.repository;


import com.example.land.domain.entity.SellLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SellLogRepository
        extends JpaRepository<SellLog, UUID> {
}
