package com.datn.ecm.service.order;

import com.datn.ecm.model.customer.CustomerDTO;
import com.datn.ecm.model.order.OrderDTO;

import java.util.List;

public interface OrderService {

    String checkout(String cartOwner);

    OrderDTO placeOrder(String orderId, String cartOwner, CustomerDTO customerDTO);

    List<OrderDTO> findAllOrders();

    List<Double> getIncomeByMonth();

}
