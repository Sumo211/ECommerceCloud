package com.datn.ecm.model;

import com.datn.ecm.dto.CustomerDTO;
import com.datn.ecm.dto.LineItemDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "order")
@Getter
@Setter
@ToString
public class Order extends BaseEntity {

    @Id
    private String id;

    private String customerId;

    private double totalPrice;

    private List<LineItemDTO> cartDetails;

    private CustomerDTO customerDTO;

    public Order() {

    }

    @Builder
    public Order(String customerId, double totalPrice, List<LineItemDTO> cartDetails, CustomerDTO customerDTO) {
        this.customerId = customerId;
        this.totalPrice = totalPrice;
        this.cartDetails = cartDetails;
        this.customerDTO = customerDTO;
    }

}
