package com.datn.ecm.service;

import com.datn.ecm.dto.CustomerDTO;
import com.datn.ecm.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    Customer createCustomer(CustomerDTO customerDTO);

    Customer updateCustomer(CustomerDTO customerDTO, Customer customer);

    void deleteCustomer(Customer customer);

    Optional<Customer> findCustomerById(String id);

    Optional<Customer> findCustomerByEmail(String email);

    List<Customer> findAllCustomers();

}
