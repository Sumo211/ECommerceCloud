package com.datn.ecm.model;

import com.datn.ecm.constant.CartEventType;
import lombok.Builder;
import lombok.Data;

@Data
public class CartEvent {

    private long id;

    private CartEventType cartEventType;

    private String ownerId;

    private long clothesId;

    private int quantity;

    public CartEvent() {

    }

    @Builder
    public CartEvent(CartEventType cartEventType, String ownerId, long clothesId, int quantity) {
        this.cartEventType = cartEventType;
        this.ownerId = ownerId;
        this.clothesId = clothesId;
        this.quantity = quantity;
    }

}