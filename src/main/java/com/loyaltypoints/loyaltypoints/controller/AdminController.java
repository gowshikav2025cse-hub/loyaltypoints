
package com.loyaltypoints.loyaltypoints.controller;

import com.loyaltypoints.loyaltypoints.entity.Customer;
import com.loyaltypoints.loyaltypoints.repository.CustomerRepository;
import com.loyaltypoints.loyaltypoints.repository.TierRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Controller
public class AdminController {

    private final CustomerRepository customerRepository;
    private final TierRepository tierRepository;

    public AdminController(
            CustomerRepository customerRepository,
            TierRepository tierRepository) {
        this.customerRepository = customerRepository;
        this.tierRepository = tierRepository;
    }

    @GetMapping("/admin")
    public String adminDashboard(
            @RequestParam(required = false) String search,
            Model model) {

        List<Customer> allCustomers = customerRepository.findAll();

        List<Customer> customers = allCustomers;

        if (search != null && !search.trim().isEmpty()) {
            String keyword = search.trim().toLowerCase(Locale.ROOT);

            customers = allCustomers.stream()
                    .filter(customer ->
                            (customer.getName() != null &&
                                    customer.getName()
                                            .toLowerCase(Locale.ROOT)
                                            .contains(keyword))
                            ||
                            (customer.getEmail() != null &&
                                    customer.getEmail()
                                            .toLowerCase(Locale.ROOT)
                                            .contains(keyword)))
                    .collect(Collectors.toList());
        }

        model.addAttribute("customers", customers);
        model.addAttribute("tiers", tierRepository.findAll());
        model.addAttribute("totalCustomers", allCustomers.size());
        model.addAttribute("totalTiers", tierRepository.findAll().size());
        model.addAttribute("search", search);

        return "admin-dashboard";
    }
}