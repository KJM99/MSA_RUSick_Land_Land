package com.example.land.service;


import com.example.land.dto.LandCreateRequest;
import com.example.land.global.domain.entity.Land;
import com.example.land.global.domain.repository.LandRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LandServiceImpl implements LandService {
    private final LandRepository landRepository;

    @Override
    @Transactional
    public void addLandbyUserId(LandCreateRequest req, Long id) {
        Land land = req.toEntity(id);
        land.setLandYN(true);
        landRepository.save(land);
    }
}
