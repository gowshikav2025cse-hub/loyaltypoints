package com.loyaltypoints.loyaltypoints.service;

import com.loyaltypoints.loyaltypoints.entity.Customer;
import com.loyaltypoints.loyaltypoints.entity.PointsLedger;
import com.loyaltypoints.loyaltypoints.repository.CustomerRepository;
import com.loyaltypoints.loyaltypoints.repository.PointsLedgerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PointsRedemptionService {

    private final CustomerRepository customerRepository;
    private final PointsLedgerRepository pointsLedgerRepository;

    public PointsRedemptionService(
            CustomerRepository customerRepository,
            PointsLedgerRepository pointsLedgerRepository) {
        this.customerRepository = customerRepository;
        this.pointsLedgerRepository = pointsLedgerRepository;
    }

    @Transactional
    public String redeemPoints(Long customerId, Integer points) {

        if (points == null || points <= 0) {
            throw new RuntimeException("Points must be greater than zero");
        }

        if (points % 100 != 0) {
            throw new RuntimeException("Points must be in multiples of 100");
        }

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        if (customer.getPointsBalance() < points) {
            throw new RuntimeException("Insufficient points balance");
        }

        customer.setPointsBalance(
                customer.getPointsBalance() - points
        );

        customerRepository.save(customer);

        PointsLedger ledger = new PointsLedger();
        ledger.setCustomer(customer);
        ledger.setTransactionType(
                PointsLedger.TransactionType.REDEEMED
        );
        ledger.setPoints(points);
        ledger.setDescription(
                "Redeemed " + points + " points for ₹" + (points / 10)
        );

        pointsLedgerRepository.save(ledger);

        return "Successfully redeemed " + points
                + " points. Discount value: ₹" + (points / 10);
    }
}