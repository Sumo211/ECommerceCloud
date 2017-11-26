package com.datn.ecm.repository;

import com.datn.ecm.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CustomerRepository extends MongoRepository<Customer, String> {

    Optional<Customer> findById(String id);

    Optional<Customer> findByAccountEmail(String email);

}
