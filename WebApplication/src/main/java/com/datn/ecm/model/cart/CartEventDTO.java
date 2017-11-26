package com.datn.ecm.model.cart;

import com.datn.ecm.constant.CartEventType;
import lombok.Data;

@Data
public class CartEventDTO {

    private CartEventType cartEventType;

    private long clothesId;

    private int quantity;

}
