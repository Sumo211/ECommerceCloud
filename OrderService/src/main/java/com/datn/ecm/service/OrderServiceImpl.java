package com.datn.ecm.service;

import com.datn.ecm.constant.CartEventType;
import com.datn.ecm.constant.InventoryStatus;
import com.datn.ecm.dto.CustomerDTO;
import com.datn.ecm.dto.LineItemDTO;
import com.datn.ecm.dto.OrderDTO;
import com.datn.ecm.model.*;
import com.datn.ecm.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger LOG = LoggerFactory.getLogger(OrderServiceImpl.class);

    private static final double VAT_TAX = 0.1;

    private static final String CART_SERVICE_URL = "http://cart-service";

    private static final String PRODUCT_SERVICE_URL = "http://product-service";

    private static final String CUSTOMER_SERVICE_URL = "http://customer-service";

    private RestTemplate restTemplate;

    private OrderRepository orderRepository;

    public OrderServiceImpl(RestTemplate restTemplate, OrderRepository orderRepository) {
        this.restTemplate = restTemplate;
        this.orderRepository = orderRepository;
    }

    @Override
    public CheckoutResult checkout(String ownerId) {
        CheckoutResult checkoutResult = new CheckoutResult();
        List<LineItem> currentCart = getShoppingCart(ownerId);
        String clothesIds = currentCart.stream().map(item -> String.valueOf(item.getClothesId())).collect(Collectors.joining(","));
        List<Integer> inventories = getCurrentInventories(clothesIds);

        if (!inventories.isEmpty()) {
            String message = checkAvailableInventory(currentCart, inventories);
            if ("available".equals(message)) {
                List<LineItemDTO> cartDetails = currentCart.stream()
                        .map(item -> LineItemDTO.builder()
                                .clothesId(item.getClothesId())
                                .name(item.getClothes().getName())
                                .price(item.getClothes().getPrice())
                                .quantity(item.getQuantity())
                                .build())
                        .collect(Collectors.toList());

                Order newOrder = Order.builder()
                        .customerId(ownerId)
                        .totalPrice(calculateTotalPrice(currentCart))
                        .cartDetails(cartDetails)
                        .build();
                checkoutResult.setMessage("order created");
                checkoutResult.setOrder(orderRepository.save(newOrder));
            } else {
                checkoutResult.setMessage(message);
            }
        } else {
            checkoutResult.setMessage("error when inventorying");
        }

        return checkoutResult;
    }

    @Override
    public OrderDTO placeOrder(String ownerId, String orderId, CustomerDTO customerDTO) {
        Order currentOrder = orderRepository.findOne(orderId);
        if (currentOrder == null) {
            return null;
        }

        if(!StringUtils.isEmpty(customerDTO.getPassword())) {
            createNewCustomer(customerDTO);
        }
        currentOrder.setCustomerDTO(customerDTO);
        Order newOrder = orderRepository.save(currentOrder);

        updateInventory(createNewInventories(currentOrder));
        clearCart(ownerId);

        return OrderDTO.builder().orderId(newOrder.getId()).dateCreated(newOrder.getCreatedAt())
                .total(newOrder.getTotalPrice()).build();
    }

    @Override
    public List<Order> getOrderHistoryOfCustomer(String ownerId) {
        return orderRepository.findByCustomerId(ownerId);
    }

    @Override
    public List<OrderDTO> findAllOrder() {
        List<OrderDTO> orderDTOs = new ArrayList<>();
        orderRepository.findAll().forEach(item -> orderDTOs.add(OrderDTO.builder()
                .orderId(item.getId())
                .customerId(item.getCustomerId())
                .customerName(item.getCustomerDTO().getName())
                .dateCreated(item.getCreatedAt())
                .total(item.getTotalPrice())
                .lineItemDTOs(item.getCartDetails())
                .build()));
        return orderDTOs;
    }

    @Override
    public List<Double> getIncomeByMonth() {
        List<Double> initial = Arrays.asList(0D, 0D, 0D, 0D, 0D, 0D, 0D, 0D, 0D, 0D, 0D, 0D);
        orderRepository.findAll().forEach(order -> {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(order.getCreatedAt());
            if(calendar.get(Calendar.YEAR) == 2016) {
                int month = calendar.get(Calendar.MONTH);
                double incomeByMonth = initial.get(month);
                incomeByMonth += order.getTotalPrice();
                initial.set(month, incomeByMonth);
            }
        });
        return initial;
    }

    private List<LineItem> getShoppingCart(String ownerId) {
        List<LineItem> currentCart = new ArrayList<>();
        try {
            ResponseEntity<List<LineItem>> entity = restTemplate.exchange(CART_SERVICE_URL + "/v1/carts/{ownerId}",
                    HttpMethod.GET, null, new ParameterizedTypeReference<List<LineItem>>() {
                    }, ownerId);
            currentCart = entity.getBody();
        } catch (HttpClientErrorException ex) {
            LOG.error("error", ex);
        }
        return currentCart;
    }

    private List<Integer> getCurrentInventories(String clothesIds) {
        List<Integer> inventories = new ArrayList<>();
        try {
            ResponseEntity<List<Integer>> entity = restTemplate.exchange(PRODUCT_SERVICE_URL + "/v1/inventories/{clothesIds}",
                    HttpMethod.GET, null, new ParameterizedTypeReference<List<Integer>>() {
                    }, clothesIds);
            inventories = entity.getBody();
        } catch (HttpClientErrorException ex) {
            LOG.error(new String(ex.getResponseBodyAsByteArray()));
        }
        return inventories;
    }

    private String checkAvailableInventory(List<LineItem> lineItems, List<Integer> inventories) {
        String message = "available";
        List<LineItem> insufficientLineItems = new ArrayList<>();

        int cartSize = lineItems.size();
        for (int i = 0; i < cartSize; i++) {
            if (inventories.get(i) - lineItems.get(i).getQuantity() < 0) {
                insufficientLineItems.add(lineItems.get(i));
            }
        }

        if (!insufficientLineItems.isEmpty()) {
            String clothesIds = insufficientLineItems.stream().map(item -> String.valueOf(item.getClothesId())).collect(Collectors.joining(","));
            message = String.format("Insufficient inventory available for %s. " + "Lower the quantity of these products and try again.", clothesIds);
        }

        return message;
    }

    private double calculateTotalPrice(List<LineItem> lineItems) {
        return lineItems.stream().mapToDouble(item -> calculateSubPrice(item.getClothes().getPrice(),
                item.getClothes().getDiscount(), item.getQuantity())).sum();
    }

    private double calculateSubPrice(double price, double discount, int quantity) {
        double beforeDiscount = price * quantity;
        double afterDiscount = beforeDiscount - (beforeDiscount * discount);
        return (afterDiscount + (afterDiscount * VAT_TAX));
    }

    private List<Inventory> createNewInventories(Order currentOrder) {
        List<Inventory> inventories = new ArrayList<>();
        List<LineItemDTO> cartDetails = currentOrder.getCartDetails();

        for (LineItemDTO item : cartDetails) {
            Inventory inventory = Inventory.builder()
                    .clothesId(item.getClothesId())
                    .quantity(item.getQuantity())
                    .status(InventoryStatus.DELIVERED)
                    .build();
            inventories.add(inventory);
        }

        return inventories;
    }

    private void updateInventory(List<Inventory> inventories) {
        try {
            restTemplate.postForEntity(PRODUCT_SERVICE_URL + "/v1/inventories/list", inventories, Object.class);
        } catch (HttpClientErrorException ex) {
            LOG.error("error", ex);
        }
    }

    private void clearCart(String ownerId) {
        try {
            CartEvent closeCart = CartEvent.builder().ownerId(ownerId).cartEventType(CartEventType.CHECK_OUT).build();
            restTemplate.postForEntity(CART_SERVICE_URL + "/v1/carts/events", closeCart, CartEvent.class);
        } catch (HttpClientErrorException ex) {
            LOG.error("error", ex);
        }
    }

    private void createNewCustomer(CustomerDTO customerDTO) {
        try {
            restTemplate.postForEntity(CUSTOMER_SERVICE_URL + "/v1/customers", customerDTO, Object.class);
        } catch (HttpClientErrorException ex) {
            LOG.error("error", ex);
        }
    }

}
