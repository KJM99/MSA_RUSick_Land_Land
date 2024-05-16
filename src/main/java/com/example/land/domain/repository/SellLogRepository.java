package com.example.land.domain.repository;


import com.example.land.domain.entity.SellLog;
import com.netflix.appinfo.ApplicationInfoManager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SellLogRepository
        extends JpaRepository<SellLog, UUID> {
    List<SellLog> findByLandId(UUID landId);
    List<SellLog> findByLandIdOrderBySellLogDateAsc(UUID landId);
}
