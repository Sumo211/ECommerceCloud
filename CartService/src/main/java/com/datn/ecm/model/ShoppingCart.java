package com.datn.ecm.model;

import com.datn.ecm.constant.CartEventType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@ToString
public class ShoppingCart {

    @Setter
    private Map<Long, Integer> productMap;

    @Setter @Getter
    private List<LineItem> lineItems;

    public ShoppingCart() {
        productMap = new HashMap<>();
        lineItems = new ArrayList<>();
    }

    @JsonIgnore
    public Map<Long, Integer> getProductMap() {
        return productMap;
    }

    public ShoppingCart incorporate(CartEvent cartEvent) {
        Flux<CartEventType> validCartEventTypes = Flux.fromStream(Stream.of(CartEventType.ADD_ITEM, CartEventType.REMOVE_ITEM));

        if(validCartEventTypes.exists(cartEventType -> cartEvent.getCartEventType().equals(cartEventType)).get()) {
            productMap.put(cartEvent.getClothesId(), productMap.getOrDefault(cartEvent.getClothesId(), 0)
                    + (cartEvent.getQuantity() * (CartEventType.ADD_ITEM.equals(cartEvent.getCartEventType()) ? 1 : -1)));
        }

        return this;
    }

    public static boolean isTerminal(CartEventType cartEventType) {
        return (cartEventType == CartEventType.CLEAR_CART || cartEventType == CartEventType.CHECK_OUT);
    }

}
