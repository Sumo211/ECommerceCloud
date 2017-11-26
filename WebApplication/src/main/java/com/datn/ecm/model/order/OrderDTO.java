package com.datn.ecm.model.order;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class OrderDTO {

    private String orderId;

    private String customerId;

    private String customerName;

    private Date dateCreated;

    private double total;

    private List<LineItemDTO> lineItemDTOs;

}
