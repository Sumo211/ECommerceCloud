package com.datn.ecm.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "customer")
@Data
@NoArgsConstructor
public class Customer {

    @Id
    private String id;

    private String name;

    private String phone;

    private Account account;

    private Address address;

    @Builder
    public Customer(String name, String phone, Account account, Address address) {
        this.name = name;
        this.phone = phone;
        this.account = account;
        this.address = address;
    }

}
