package com.example.payments.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "settlement_point")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SettlementPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID pointId;

    @Column(name = "client_tin")
    private String clientTin;

    @Column(name = "client_name")
    private String clientName;

    @Column(name = "activity")
    private String activity;

    @Column(name = "object_type")
    private String objectType;

    @Column(name = "point_type")
    private String pointType;

    @Column(name = "point_format")
    private String pointFormat;

    @Column(name = "equipment_type")
    private String equipmentType;

    @Column(name = "equipment_id")
    private String equipmentId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "point_address_id")
    private PointAddress pointAddress;

    @Column
    private LocalDate created;

    @Column
    private LocalDate updated;
}
