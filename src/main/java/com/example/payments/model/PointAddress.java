package com.example.payments.model;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "point_address")
public class PointAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "country")
    private String country;

    @Column(name = "administrative_area")
    private String administrativeArea;

    @Column(name = "locality")
    private String locality;

    @Column(name = "route")
    private String route;

    @Column(name = "street_number")
    private String streetNumber;

    @Column(name = "location")
    private String location;
}
