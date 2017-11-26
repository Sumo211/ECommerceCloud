package com.datn.ecm.model;

import lombok.Data;

@Data
public class CheckoutResult {

    private String message;

    private Order order;

    public CheckoutResult() {

    }

}
