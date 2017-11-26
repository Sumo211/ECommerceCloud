package com.datn.ecm.service.customer;

import com.datn.ecm.model.customer.Customer;
import com.datn.ecm.model.customer.CustomerDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

@Service
public class CustomerServiceImpl implements CustomerService {

    private static final Logger LOG = LoggerFactory.getLogger(CustomerServiceImpl.class);

    private static final String CUSTOMER_SERVICE_URL = "http://customer-service";

    private RestTemplate restTemplate;

    public CustomerServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Optional<Customer> findCustomerByEmail(String email) {
        Customer customer = null;
        try {
            Map<String, String> uriParams = new LinkedHashMap<>();
            UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(CUSTOMER_SERVICE_URL + "/v1/customers/find-by-email")
                    .queryParam("email", email);
            ResponseEntity<Customer> entity = restTemplate.exchange(builder.buildAndExpand(uriParams).toUri(),
                    HttpMethod.GET, null, Customer.class);
            customer = entity.getBody();
        } catch (HttpClientErrorException ex) {
            LOG.error(ex.getResponseBodyAsString());
        }
        return Optional.ofNullable(customer);
    }

    @Override
    public List<Customer> findAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        try {
            ResponseEntity<List<Customer>> entity = restTemplate.exchange(CUSTOMER_SERVICE_URL + "/v1/customers",
                    HttpMethod.GET, null, new ParameterizedTypeReference<List<Customer>>() {
                    });
            customers = entity.getBody();
        } catch (HttpClientErrorException ex) {
            LOG.error(ex.getResponseBodyAsString());
        }
        return customers;
    }

    @Override
    public void deleteCustomer(String id) {
        System.out.println("here" + id);
        try {
            restTemplate.delete(CUSTOMER_SERVICE_URL + "/v1/customers/{id}", id);
        } catch (HttpClientErrorException ex) {
            LOG.error(ex.getResponseBodyAsString());
        }
    }

    @Override
    public Optional<Customer> createNewCustomer(CustomerDTO customerDTO) {
        Customer customer = null;
        try {
            ResponseEntity<Customer> entity = restTemplate.postForEntity(CUSTOMER_SERVICE_URL + "/v1/customers", customerDTO, Customer.class);
            customer = entity.getBody();
        } catch (HttpClientErrorException ex) {
            LOG.error("error", ex);
        }
        return Optional.ofNullable(customer);
    }

}
