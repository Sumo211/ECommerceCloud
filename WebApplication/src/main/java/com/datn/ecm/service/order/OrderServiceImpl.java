package com.datn.ecm.service.order;

import com.datn.ecm.model.customer.CustomerDTO;
import com.datn.ecm.model.order.OrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger LOG = LoggerFactory.getLogger(OrderServiceImpl.class);

    private static final String ORDER_SERVICE_URL = "http://order-service";

    private static final String FAIL = "fail";

    private RestTemplate restTemplate;

    public OrderServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String checkout(String cartOwner) {
        String result;
        try {
            ResponseEntity<String> entity = restTemplate.postForEntity(ORDER_SERVICE_URL + "/v1/checkout/{ownerId}", null, String.class, cartOwner);
            result = entity.getBody();
        } catch (HttpClientErrorException ex) {
            LOG.error("error", ex);
            result = FAIL;
        }
        return result;
    }

    @Override
    public OrderDTO placeOrder(String orderId, String cartOwner, CustomerDTO customerDTO) {
        OrderDTO result = null;
        try {
            ResponseEntity<OrderDTO> entity = restTemplate.postForEntity(ORDER_SERVICE_URL + "/v1/orders/{ownerId}/{orderId}",
                    customerDTO, OrderDTO.class, cartOwner, orderId);
            result = entity.getBody();
        } catch (HttpClientErrorException ex) {
            LOG.error("error", ex);
        }
        return result;
    }

    @Override
    public List<OrderDTO> findAllOrders() {
        List<OrderDTO> orders = new ArrayList<>();
        try {
            ResponseEntity<List<OrderDTO>> entity = restTemplate.exchange(ORDER_SERVICE_URL + "/v1/orders",
                    HttpMethod.GET, null, new ParameterizedTypeReference<List<OrderDTO>>() {
                    });
            orders = entity.getBody();
        } catch (HttpClientErrorException ex) {
            LOG.error(ex.getResponseBodyAsString());
        }
        return orders;
    }

    @Override
    public List<Double> getIncomeByMonth() {
        List<Double> incomes = Arrays.asList(0D, 0D, 0D, 0D, 0D, 0D, 0D, 0D, 0D, 0D, 0D, 0D);
        try {
            ResponseEntity<List<Double>> entity = restTemplate.exchange(ORDER_SERVICE_URL + "/v1/orders/get-income-by-month",
                    HttpMethod.GET, null, new ParameterizedTypeReference<List<Double>>() {
                    });
            incomes = entity.getBody();
        } catch (HttpClientErrorException ex) {
            LOG.error(ex.getResponseBodyAsString());
        }
        return incomes;
    }

}
