package com.datn.ecm.model.customer;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class Address implements Serializable {

    private String street;

    private String city;

    private String country;

    private int zipCode;

}
