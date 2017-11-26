package com.datn.ecm.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Account {

    private String email;

    private String password;

    private int role;

    @Builder
    public Account(String email, String password, int role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

}
