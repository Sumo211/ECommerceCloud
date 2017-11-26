package com.datn.ecm.controller;

import com.datn.ecm.controller.exception.CustomerNotFoundException;
import com.datn.ecm.controller.exception.EmailExistException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomerControllerAdvice {

    @ExceptionHandler(EmailExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleEmailIsExistException(EmailExistException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleCustomerNotFoundException(CustomerNotFoundException ex) {
        return ex.getMessage();
    }

}
