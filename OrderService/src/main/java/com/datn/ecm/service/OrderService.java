package com.datn.ecm.service;

import com.datn.ecm.dto.CustomerDTO;
import com.datn.ecm.dto.OrderDTO;
import com.datn.ecm.model.CheckoutResult;
import com.datn.ecm.model.Order;

import java.util.List;

public interface OrderService {

    CheckoutResult checkout(String ownerId);

    OrderDTO placeOrder(String ownerId, String orderId, CustomerDTO customerDTO);

    List<Order> getOrderHistoryOfCustomer(String ownerId);

    List<OrderDTO> findAllOrder();

    List<Double> getIncomeByMonth();

}
