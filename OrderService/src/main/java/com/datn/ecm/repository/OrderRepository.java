package com.datn.ecm.repository;

import com.datn.ecm.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {

    List<Order> findByCustomerId(String ownerId);

}
