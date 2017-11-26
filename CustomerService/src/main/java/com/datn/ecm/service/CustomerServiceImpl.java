package com.datn.ecm.service;

import com.datn.ecm.dto.CustomerDTO;
import com.datn.ecm.model.Account;
import com.datn.ecm.model.Address;
import com.datn.ecm.model.Customer;
import com.datn.ecm.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer createCustomer(CustomerDTO customerDTO) {
        Account account = Account.builder().email(customerDTO.getEmail())
                .password(customerDTO.getPassword())
                .role(2).build();
        Address address = Address.builder().street(customerDTO.getAddress())
                .city(customerDTO.getCity())
                .country("VN")
                .zipCode(550000).build();
        Customer customer = Customer.builder().name(customerDTO.getName())
                .phone(customerDTO.getPhone())
                .account(account)
                .address(address).build();

        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(CustomerDTO customerDTO, Customer customer) {
        Account account = Account.builder().password(customerDTO.getPassword()).role(2).build();
        Address address = Address.builder().street(customerDTO.getAddress())
                .city(customerDTO.getCity())
                .country("VN")
                .zipCode(1).build();
        customer = Customer.builder().name(customerDTO.getName())
                .phone(customerDTO.getPhone())
                .account(account)
                .address(address).build();
        return customerRepository.save(customer);
    }

    @Override
    public void deleteCustomer(Customer customer) {
        customerRepository.delete(customer);
    }

    @Override
    public Optional<Customer> findCustomerById(String id) {
        return customerRepository.findById(id);
    }

    @Override
    public Optional<Customer> findCustomerByEmail(String email) {
        return customerRepository.findByAccountEmail(email);
    }

    @Override
    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

}
