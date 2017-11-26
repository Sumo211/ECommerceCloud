package com.datn.ecm.model.order;

import com.datn.ecm.model.product.Clothes;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
public class LineItem implements Serializable {

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