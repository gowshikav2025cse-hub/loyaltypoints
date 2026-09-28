package com.loyaltypoints.loyaltypoints.controller;

import com.loyaltypoints.loyaltypoints.entity.PointsLedger;
import com.loyaltypoints.loyaltypoints.service.PointsLedgerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/points")
public class PointsLedgerController {

    private final PointsLedgerService pointsLedgerService;

    public PointsLedgerController(PointsLedgerService pointsLedgerService) {
        this.pointsLedgerService = pointsLedgerService;
    }

    @GetMapping
    public List<PointsLedger> getAllTransactions() {
        return pointsLedgerService.getAllTransactions();
    }

    @GetMapping("/customer/{customerId}")
    public List<PointsLedger> getTransactionsByCustomer(
            @PathVariable Long customerId) {
        return pointsLedgerService.getTransactionsByCustomer(customerId);
    }
}