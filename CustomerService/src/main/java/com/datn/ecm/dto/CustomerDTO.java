package com.datn.ecm.dto;

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

}