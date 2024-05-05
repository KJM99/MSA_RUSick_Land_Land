package com.example.land.global.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Table(name = "SELLLOGS")
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SellLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SELLLOG_ID")
    private Long id;

    @Column(name= "SELLLOG_DATE", nullable = false)
    private LocalDateTime sellLogDate;

    @Column(name= "SELLLOG_PRICE", nullable = false)
    private Long sellLogPrice;

    //manytoone 매물 아이디
    @ManyToOne
    @JoinColumn(name="LAND_ID", nullable = false)
    private Land land;
}
