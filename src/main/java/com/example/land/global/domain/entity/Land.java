package com.example.land.global.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Table(name = "LANDS")
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Land {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LAND_ID")
    private Long id;

    @Column(name="OWNER_ID", nullable = false)
    private Long ownerId;

    @Column(name="OWNER_NAME", nullable = false)
    private String ownerName;

    @Column(name="LAND_NAME", nullable = false)
    private String landName;

    @Column(name="LAND_CATEGORY", nullable = false)
    private int landCategory;

    @Column(name="LAND_AREA", nullable = false)
    private String landArea;

    @Column(name="LAND_DESCRIPTION")
    private String landDescription;

    @Column(name="LAND_ADDRESS", nullable = false)
    private String landAddress;

    @Column(name="LAND_DETAIL_ADDRESS")
    private String landDetailAddress;

    @Column(name="LAND_PRICE", nullable = false)
    private Long landPrice;

    @Column(name="LAND_BUILT_DATE", nullable = false)
    private LocalDateTime landBuiltDate;

    @Column(name="LAND_YN", nullable = false) @Setter
    private boolean landYN;

    //onetomany 관심 매물 아이디
    @OneToMany(mappedBy = "land", cascade = CascadeType.REMOVE)
    private List<InterestLand> interestLands;

    //onetomany 매매 기록
    @OneToMany(mappedBy = "land")
    private List<SellLog> sellLogs;
}
