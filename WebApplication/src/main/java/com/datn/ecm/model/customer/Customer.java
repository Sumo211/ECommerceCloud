package com.datn.ecm.model.customer;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class Customer implements Serializable{

    private String id;

    private String name;

    private String phone;

    private Account account;

    private Address address;

}
