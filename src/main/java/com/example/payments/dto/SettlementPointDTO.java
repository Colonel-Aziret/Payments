package com.example.payments.dto;

import jakarta.persistence.Column;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Getter
@Setter
public class SettlementPointDTO {
    private String clientTin;

    private String clientName;

    private String activity;

    private String objectType;

    private String pointType;

    private String pointFormat;

    private String equipmentType;

    private String equipmentId;

    private LocalDate created;

    private LocalDate updated;

    private PointAddressDTO pointAddress;

}
