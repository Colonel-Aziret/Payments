package com.example.payments.model;

import jakarta.persistence.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "operations")
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JoinColumn(name = "point_id", referencedColumnName = "pointId")
    private UUID pointId;

    @Column(name = "date")
    private Date date;

    @Column(name = "sum")
    private Double sum;

    @Column(name = "currency")
    private String currency;

    @Column(name = "commission")
    private Double commission;

    @Column(name = "supplier_tin")
    private String supplierTin;

    @Column(name = "supplier_name")
    private String supplierName;

    @Column(name = "nonresident_name")
    private String nonresidentName;

    @Column(name = "payment_details")
    private String paymentDetails;

    @Column(name = "payment_type")
    private Integer paymentType;

    public Object getKey(Object any) {
        return any;
    }
}

