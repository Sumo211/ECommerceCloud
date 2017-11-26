package com.datn.ecm.model.customer;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerDTO {

    private String name;

    private String phone;

    private String email;

    private String password;

    private String address;

    private String city;

    @Builder
    public CustomerDTO(String name, String phone, String email, String password, String address, String city) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.address = address;
        this.city = city;
    }

}