package com.example.payments.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class PointAddressDTO {
    private String postalCode;

    private String country;

    private String administrativeArea;

    private String locality;

    private String route;

    private String streetNumber;

    private String location;
}

