package com.loyaltypoints.loyaltypoints.controller;

import com.loyaltypoints.loyaltypoints.entity.Purchase;
import com.loyaltypoints.loyaltypoints.service.PurchaseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchases")
public class PurchaseController {

    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping("/{customerId}")
    public Purchase createPurchase(
            @PathVariable Long customerId,
            @RequestParam Double amount) {
        return purchaseService.processPurchase(customerId, amount);
    }

    @GetMapping
    public List<Purchase> getAllPurchases() {
        return purchaseService.getAllPurchases();
    }

    @GetMapping("/{id}")
    public Purchase getPurchaseById(@PathVariable Long id) {
        return purchaseService.getPurchaseById(id);
    }

    @GetMapping("/customer/{customerId}")
    public List<Purchase> getPurchasesByCustomer(
            @PathVariable Long customerId) {
        return purchaseService.getPurchasesByCustomer(customerId);
    }
}