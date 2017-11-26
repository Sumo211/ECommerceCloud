package com.datn.ecm.controller;

import com.datn.ecm.dto.CustomerDTO;
import com.datn.ecm.dto.OrderDTO;
import com.datn.ecm.model.CheckoutResult;
import com.datn.ecm.model.Order;
import com.datn.ecm.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/v1")
public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping(value = "/checkout/{ownerId}")
    public ResponseEntity checkout(@PathVariable String ownerId) {
        CheckoutResult result = orderService.checkout(ownerId);
        String finalMessage = result.getMessage();
        if (result.getOrder() != null) {
            finalMessage = finalMessage + ":" + result.getOrder().getId();
        }
        return ResponseEntity.status(HttpStatus.OK).body(finalMessage);
    }

    @PostMapping(value = "/orders/{ownerId}/{orderId}")
    public ResponseEntity placeOrder(@PathVariable String ownerId, @PathVariable String orderId, @RequestBody CustomerDTO customerDTO) {
        return Optional.ofNullable(orderService.placeOrder(ownerId, orderId, customerDTO))
                .map(orderDTO -> ResponseEntity.status(HttpStatus.OK).body(orderDTO))
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @GetMapping(value = "/orders/{ownerId}")
    public ResponseEntity getOrderHistoryOfCustomer(@PathVariable String ownerId) {
        List<Order> orders = orderService.getOrderHistoryOfCustomer(ownerId);
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }

    @GetMapping(value = "/orders")
    public ResponseEntity findAllOrders() {
        List<OrderDTO> orderDTOs = orderService.findAllOrder();

        /*if(orderDTOs.isEmpty()) {
            throw new RuntimeException("cannot found orders");
        }*/

        return ResponseEntity.status(HttpStatus.OK).body(orderDTOs);
    }

    @GetMapping(value = "/orders/get-income-by-month")
    public ResponseEntity getIncomeByMonth() {
        List<Double> incomes = orderService.getIncomeByMonth();
        return ResponseEntity.status(HttpStatus.OK).body(incomes);
    }

}
