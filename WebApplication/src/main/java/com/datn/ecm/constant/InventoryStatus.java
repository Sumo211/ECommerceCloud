package com.datn.ecm.constant;

public enum InventoryStatus {

    RESERVED(1),
    DELIVERED(-1);

    private int value;

    InventoryStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
