package com.datn.ecm.controller;

import com.datn.ecm.model.CartEvent;
import com.datn.ecm.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/v1/carts")
public class CartController {

    private CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping(value = "/events")
    public ResponseEntity addCartEvent(@RequestBody CartEvent cartEvent) {
        cartService.addCartEvent(cartEvent);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(value = "/{ownerId}")
    public ResponseEntity getCart(@PathVariable String ownerId) {
        return Optional.ofNullable(cartService.getShoppingCart(ownerId))
                .map(cart -> ResponseEntity.status(HttpStatus.OK).body(cart.getLineItems()))
                .orElseThrow(() -> new RuntimeException("Cannot found cart"));
    }

    /*@PostMapping(value = "/{sessionid}/{customerid}")
    public ResponseEntity updateOwnerId(@PathVariable String sessionid, @PathVariable String customerid) {
        cartService.updateOwnerId(sessionid, customerid);
        return ResponseEntity.status(HttpStatus.OK).build();
    }*/

}
