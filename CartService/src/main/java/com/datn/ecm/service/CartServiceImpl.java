package com.datn.ecm.service;

import com.datn.ecm.model.CartEvent;
import com.datn.ecm.model.Clothes;
import com.datn.ecm.model.LineItem;
import com.datn.ecm.model.ShoppingCart;
import com.datn.ecm.repository.CartEventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    private static final Logger LOG = LoggerFactory.getLogger(CartServiceImpl.class);

    private static final String PRODUCT_SERVICE_URL = "http://product-service";

    private CartEventRepository cartEventRepository;

    private RestTemplate restTemplate;

    public CartServiceImpl(CartEventRepository cartEventRepository, RestTemplate restTemplate) {
        this.cartEventRepository = cartEventRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public CartEvent addCartEvent(CartEvent cartEvent) {
        return cartEventRepository.save(cartEvent);
    }

    @Override
    public ShoppingCart getShoppingCart(String ownerId) {
        return aggregateCartEvents(ownerId);
    }

    /*@Override
    public void updateOwnerId(String sessionid, String customerid) {
        List<CartEvent> cartEvents = cartEventRepository.findByOwnerId(sessionid);
        if(cartEvents.isEmpty()) {
            return;
        }
        cartEvents.forEach(item -> item.setOwnerId(customerid));
        cartEventRepository.save(cartEvents);
    }*/

    private ShoppingCart aggregateCartEvents(String ownerId) {
        Flux<CartEvent> cartEvents = Flux.fromStream(cartEventRepository.getCartEventStreamByUser(ownerId));
        ShoppingCart shoppingCart = cartEvents.takeWhile(cartEvent -> !ShoppingCart.isTerminal(cartEvent.getCartEventType()))
                .reduceWith(ShoppingCart::new, ShoppingCart::incorporate)
                .get();

        List<LineItem> lineItems = shoppingCart.getProductMap().entrySet()
                .stream()
                .map(item -> LineItem.builder()
                        .clothesId(item.getKey())
                        .quantity(item.getValue())
                        .build())
                .filter(item -> item.getQuantity() > 0)
                .collect(Collectors.toList());

        String clothesIds = lineItems.stream().map(item -> String.valueOf(item.getClothesId())).collect(Collectors.joining(","));
        List<Clothes> clothes = getClothesInShoppingCart(clothesIds);

        for (int i = 0; i < lineItems.size(); i++) {
            if (clothes.get(i) != null) {
                lineItems.get(i).setClothes(clothes.get(i));
            }
        }

        shoppingCart.setLineItems(lineItems);

        return shoppingCart;
    }

    private List<Clothes> getClothesInShoppingCart(String clothesIds) {
        List<Clothes> clothes = new ArrayList<>();
        try {
            Map<String, String> uriParams = new LinkedHashMap<>();
            UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(PRODUCT_SERVICE_URL + "/v1/clothes/get-in-cart")
                    .queryParam("clothesIds", clothesIds);
            ResponseEntity<List<Clothes>> entity = restTemplate.exchange(builder.buildAndExpand(uriParams).toUri(),
                    HttpMethod.GET, null, new ParameterizedTypeReference<List<Clothes>>() {
                    });
            clothes = entity.getBody();
        } catch (HttpClientErrorException ex) {
            LOG.error(new String(ex.getResponseBodyAsByteArray()));
        }
        return clothes;
    }

}