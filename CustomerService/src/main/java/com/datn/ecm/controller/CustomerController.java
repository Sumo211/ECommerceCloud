package com.datn.ecm.controller;

import com.datn.ecm.controller.exception.CustomerNotFoundException;
import com.datn.ecm.controller.exception.EmailExistException;
import com.datn.ecm.dto.CustomerDTO;
import com.datn.ecm.model.Customer;
import com.datn.ecm.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/customers")
public class CustomerController {

    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping(value = "/find-by-email")
    public ResponseEntity<Customer> findCustomer(String email) {
        Customer customer = customerService.findCustomerByEmail(email).orElseThrow(CustomerNotFoundException::new);
        return ResponseEntity.status(HttpStatus.OK).body(customer);
    }

    @GetMapping
    public ResponseEntity<List<Customer>> findAllCustomers() {
        List<Customer> customers = customerService.findAllCustomers();

        if(customers.isEmpty()) {
            throw new CustomerNotFoundException();
        }

        return ResponseEntity.status(HttpStatus.OK).body(customers);
    }

    @PostMapping
    public ResponseEntity createCustomer(@RequestBody CustomerDTO customerDTO) {
        validateNewAccount(customerDTO.getEmail());
        Customer customer = customerService.createCustomer(customerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(customer);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity updateCustomer(@PathVariable String id, @RequestBody CustomerDTO customerDTO) {
        validateNewAccount(customerDTO.getEmail());
        Customer currentCustomer = customerService.findCustomerById(id).orElseThrow(CustomerNotFoundException::new);
        customerService.updateCustomer(customerDTO, currentCustomer);
        return ResponseEntity.status(HttpStatus.OK).body("updated");
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteCustomer(@PathVariable String id) {
        Customer customer = customerService.findCustomerById(id).orElseThrow(CustomerNotFoundException::new);
        customerService.deleteCustomer(customer);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
    }

    private void validateNewAccount(String email) {
        if (customerService.findCustomerByEmail(email).isPresent()) {
            throw new EmailExistException(email);
        }
    }

}
