package com.datn.ecm.model.order;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LineItemDTO {

    private long clothesId;

    private String name;

    private double price;

    private int quantity;

}
