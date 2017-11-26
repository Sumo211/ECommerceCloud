package com.datn.ecm.controller.exception;

public class ClothesNotFoundException extends RuntimeException {

    public ClothesNotFoundException() {
        super("Clothes not found");
    }

}
