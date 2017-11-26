package com.datn.ecm.model;

import com.datn.ecm.constant.InventoryStatus;
import lombok.Builder;
import lombok.Data;

@Data
public class Inventory {

    private long id;

    private long clothesId;

    private int quantity;

    private InventoryStatus status;

    public Inventory() {

    }

    @Builder
    public Inventory(long clothesId, int quantity, InventoryStatus status) {
        this.clothesId = clothesId;
        this.quantity = quantity;
        this.status = status;
    }

}
