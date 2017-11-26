package com.datn.ecm.model;

import lombok.Builder;
import lombok.Data;

@Data
public class LineItem {

    private long clothesId;

    private Clothes clothes;

    private int quantity;

    public LineItem() {

    }

    @Builder
    public LineItem(long clothesId, Clothes clothes, int quantity) {
        this.clothesId = clothesId;
        this.clothes = clothes;
        this.quantity = quantity;
    }

}