package com.datn.ecm.service.cart;

import com.datn.ecm.model.cart.CartEvent;
import com.datn.ecm.model.order.LineItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    private static final Logger LOG = LoggerFactory.getLogger(CartServiceImpl.class);

    private static final String CART_SERVICE_URL = "http://cart-service";

    private static final String FAIL = "fail";

    private static final String CREATED = "created";

    private RestTemplate restTemplate;

    public CartServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String saveEvent(CartEvent cartEvent) {
        String result = CREATED;
        try {
            restTemplate.postForEntity(CART_SERVICE_URL + "/v1/carts/events", cartEvent, CartEvent.class);
        } catch (HttpClientErrorException ex) {
            LOG.error("error", ex);
            result = FAIL;
        }
        return result;
    }

    @Override
    public List<LineItem> getShoppingCart(String cartOwner) {
        List<LineItem> shoppingCart = new ArrayList<>();
        try {
            ResponseEntity<List<LineItem>> entity = restTemplate.exchange(CART_SERVICE_URL + "/v1/carts/{ownerId}",
                    HttpMethod.GET, null, new ParameterizedTypeReference<List<LineItem>>() {
                    }, cartOwner);
            shoppingCart = entity.getBody();
        } catch (HttpClientErrorException ex) {
            LOG.error(new String(ex.getResponseBodyAsByteArray()));
        }
        return shoppingCart;
    }

    /*@Override
    public String updateOwnerId(String sessionId, String customerId) {
        String result = "success";
        try {
            restTemplate.postForEntity(CART_SERVICE_URL + "/v1/carts/{sessionid}/{customerid}", null, Object.class, sessionId, customerId);
        } catch (HttpClientErrorException ex) {
            LOG.error("error", ex);
            result = FAIL;
        }
        return result;
    }*/

}
