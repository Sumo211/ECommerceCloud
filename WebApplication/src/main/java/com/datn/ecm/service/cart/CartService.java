package com.datn.ecm.service.cart;

import com.datn.ecm.model.cart.CartEvent;
import com.datn.ecm.model.order.LineItem;

import java.util.List;

public interface CartService {

    String saveEvent(CartEvent cartEvent);

    List<LineItem> getShoppingCart(String cartOwner);

    //String updateOwnerId(String sessionId, String customerId);

}
