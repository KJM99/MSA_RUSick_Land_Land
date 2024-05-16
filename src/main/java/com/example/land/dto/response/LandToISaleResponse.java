package com.example.land.dto.response;

import com.example.land.domain.entity.Land;
import java.util.UUID;

public record LandToISaleResponse(
    UUID ownerId, Long count
) {
    // public static LandToISaleResponse from(Land land){
    //     return new LandToISaleResponse(
    //         land.getOwnerId(),
    //         land.getLandName()
    //     );
    // }
}
