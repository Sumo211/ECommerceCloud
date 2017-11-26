package com.datn.ecm.controller.exception;

public class EmailExistException extends RuntimeException {

    public EmailExistException(String email) {
        super("Email '" + email + "' is exist");
    }

}
