package com.example.land.controller;

import com.example.land.LandApplication;
import com.example.land.dto.LandCreateRequest;
import com.example.land.service.LandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/lands")
public class LandController {

    private final LandService landService;

    @PostMapping
    public void addLandbyUserId(
            @RequestBody LandCreateRequest req,
            @RequestParam Long id){
        landService.addLandbyUserId(req,id);
    }




}
