package com.example.land.global.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Table(name = "INTERETST_LANDS")
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InterestLand {

    @Id
    @GeneratedValue
    @Column(name = "INTEREST_LAND_ID")
    private UUID id;

    @Column(name = "USER_ID", nullable = false)
    private UUID userId;

    //manytoOne 매물 아이디
    @ManyToOne
    @JoinColumn(name="LAND_ID", nullable = false)
    private Land land;
}
