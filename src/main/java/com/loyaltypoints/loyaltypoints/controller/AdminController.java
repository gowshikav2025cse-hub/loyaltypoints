
package com.loyaltypoints.loyaltypoints.controller;

import com.loyaltypoints.loyaltypoints.repository.CustomerRepository;
import com.loyaltypoints.loyaltypoints.repository.TierRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final CustomerRepository customerRepository;
    private final TierRepository tierRepository;

    public AdminController(
            CustomerRepository customerRepository,
            TierRepository tierRepository) {

        this.customerRepository = customerRepository;
        this.tierRepository = tierRepository;
    }

    @GetMapping
    public String adminDashboard(
            @RequestParam(required = false) String search,
            Model model) {

        var customers = customerRepository.findAll();

        if (search != null && !search.isBlank()) {
            String keyword = search.trim().toLowerCase();

            customers = customers.stream()
                    .filter(customer ->
                            customer.getName().toLowerCase().contains(keyword)
                            || customer.getEmail().toLowerCase().contains(keyword))
                    .toList();
        }

        model.addAttribute("customers", customers);
        model.addAttribute("tiers", tierRepository.findAll());
        model.addAttribute("totalCustomers", customerRepository.count());
        model.addAttribute("totalTiers", tierRepository.count());
        model.addAttribute("search", search == null ? "" : search);

        return "admin-dashboard";
    }
}