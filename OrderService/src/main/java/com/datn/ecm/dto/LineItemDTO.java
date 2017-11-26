package com.datn.ecm.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class LineItemDTO {

    private long clothesId;

    private String name;

    private double price;

    private int quantity;

    public LineItemDTO() {

    }

    @Builder
    public LineItemDTO(long clothesId, String name, int quantity, double price) {
        this.clothesId = clothesId;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

}
