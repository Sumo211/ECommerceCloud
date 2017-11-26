package com.datn.ecm.dto;

import lombok.Builder;
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

    @Builder
    public OrderDTO(String orderId, String customerId, String customerName,
                    Date dateCreated, double total, List<LineItemDTO> lineItemDTOs) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.dateCreated = dateCreated;
        this.total = total;
        this.lineItemDTOs = lineItemDTOs;
    }

}
