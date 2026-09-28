package com.loyaltypoints.loyaltypoints.controller;

import com.loyaltypoints.loyaltypoints.entity.Customer;
import com.loyaltypoints.loyaltypoints.repository.CustomerRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final CustomerRepository customerRepository;

    public DashboardController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping("/")
    public String dashboard(Authentication authentication, Model model) {

        String email = authentication.getName();

        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() ->
                        new IllegalArgumentException("Customer not found"));

        model.addAttribute("customer", customer);
        model.addAttribute("customerId", customer.getId());

        return "dashboard";
    }
}