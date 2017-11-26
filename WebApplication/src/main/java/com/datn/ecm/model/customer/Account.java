package com.datn.ecm.model.customer;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class Account implements Serializable {

    private String email;

    private String password;

    private int role;

}
