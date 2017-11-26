package com.datn.ecm.service;

import com.datn.ecm.model.CartEvent;
import com.datn.ecm.model.ShoppingCart;

public interface CartService {

    CartEvent addCartEvent(CartEvent cartEvent);

    ShoppingCart getShoppingCart(String ownerId);

    //void updateOwnerId(String sessionid, String customerid);

}