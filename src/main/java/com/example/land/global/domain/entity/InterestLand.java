package com.example.land.global.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "INTERETST_LANDS")
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InterestLand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INTEREST_LAND_ID")
    private Long id;

    @Column(name = "USER_ID", nullable = false)
    private Long userId;

    //manytoOne 매물 아이디
    @ManyToOne
    @JoinColumn(name="LAND_ID", nullable = false)
    private Land land;
}
