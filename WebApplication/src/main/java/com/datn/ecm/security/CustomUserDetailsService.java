package com.datn.ecm.security;

import com.datn.ecm.service.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private CustomerService customerService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return customerService.findCustomerByEmail(email)
                .map(customer -> new CustomUserDetails(customer))
                .orElseThrow(() -> new UsernameNotFoundException("Could not find " + email));
    }

}
