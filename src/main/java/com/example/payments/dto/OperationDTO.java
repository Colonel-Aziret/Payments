package com.example.payments.dto;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class OperationDTO {
    private UUID pointId;
    private Date date;
    private Double sum;
    private String currency;
    private Double commission;
    private String supplierTin;
    private String supplierName;
    private String nonresidentName;
    private String paymentDetails;
    private Integer paymentType;
}
