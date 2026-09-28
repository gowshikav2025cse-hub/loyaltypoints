package com.loyaltypoints.loyaltypoints.controller;

import com.loyaltypoints.loyaltypoints.entity.Customer;
import com.loyaltypoints.loyaltypoints.repository.CustomerRepository;
import com.loyaltypoints.loyaltypoints.repository.TierRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    private final CustomerRepository customerRepository;
    private final TierRepository tierRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(
            CustomerRepository customerRepository,
            TierRepository tierRepository,
            PasswordEncoder passwordEncoder) {

        this.customerRepository = customerRepository;
        this.tierRepository = tierRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/signup")
    public String signupPage() {
        return "signup";
    }

    @PostMapping("/signup")
    public String registerCustomer(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String confirmPassword,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (name.isBlank() || email.isBlank()
                || password.isBlank() || confirmPassword.isBlank()) {
            model.addAttribute("error", "All fields are required.");
            return "signup";
        }

        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match.");
            return "signup";
        }

        if (password.length() < 8) {
            model.addAttribute("error",
                    "Password must contain at least 8 characters.");
            return "signup";
        }

        if (customerRepository.existsByEmail(email)) {
            model.addAttribute("error",
                    "An account with this email already exists.");
            return "signup";
        }

        Customer customer = new Customer();
        customer.setName(name.trim());
        customer.setEmail(email.trim().toLowerCase());
        customer.setPassword(passwordEncoder.encode(password));
        customer.setPointsBalance(0);
        customer.setRole("CUSTOMER");

        tierRepository.findByName("Silver")
                .ifPresent(customer::setTier);

        customerRepository.save(customer);

        redirectAttributes.addFlashAttribute(
                "success", "Account created successfully. Please sign in.");

        return "redirect:/login";
    }
}