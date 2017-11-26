package com.datn.ecm.controller;

import com.datn.ecm.model.customer.Customer;
import com.datn.ecm.model.customer.CustomerDTO;
import com.datn.ecm.security.SecurityService;
import com.datn.ecm.service.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private SecurityService securityService;

    @GetMapping(value = "/login")
    public String showLoginPage(CustomerDTO customerDTO) {
        return "home/login";
    }

    @PostMapping(value = "/sign-up")
    public String signUp(CustomerDTO customerDTO) {
        Customer customer = customerService.createNewCustomer(customerDTO).orElseThrow(RuntimeException::new);
        securityService.autoLogin(customer.getAccount().getEmail(), customer.getAccount().getPassword());
        return "redirect:/";
    }

    @GetMapping(value = "/about-us")
    public String aboutUs() {
        return "home/about";
    }

    @GetMapping(value = "/contact")
    public String contact() {
        return "home/contact";
    }

    @GetMapping(value = "/error")
    public String error() {
        return "home/error";
    }

    @GetMapping(value = "/test-sec")
    public String testSec() { return "home/test-sec"; }

    @GetMapping(value = "/my-account")
    public String myAccount() {
        return "home/account";
    }

}
