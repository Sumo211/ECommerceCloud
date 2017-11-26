package com.datn.ecm.service.customer;

import com.datn.ecm.model.customer.Customer;
import com.datn.ecm.model.customer.CustomerDTO;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    Optional<Customer> findCustomerByEmail(String email);

    List<Customer> findAllCustomers();

    void deleteCustomer(String id);

    Optional<Customer> createNewCustomer(CustomerDTO customerDTO);

}

