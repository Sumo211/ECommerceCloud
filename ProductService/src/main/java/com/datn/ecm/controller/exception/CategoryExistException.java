package com.datn.ecm.controller.exception;

public class CategoryExistException extends RuntimeException {

    public CategoryExistException(String cName) {
        super("Category with name '" + cName + "' is exist.");
    }

}
