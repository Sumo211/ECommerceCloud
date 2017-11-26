package com.datn.ecm.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Address {

    private String street;

    private String city;

    private String country;

    private int zipCode;

    @Builder
    public Address(String street, String city, String country, int zipCode) {
        this.street = street;
        this.city = city;
        this.country = country;
        this.zipCode = zipCode;
    }

}
