package com.loyaltypoints.loyaltypoints.service;

import com.loyaltypoints.loyaltypoints.entity.Customer;
import com.loyaltypoints.loyaltypoints.entity.PointsLedger;
import com.loyaltypoints.loyaltypoints.entity.Purchase;
import com.loyaltypoints.loyaltypoints.entity.Tier;
import com.loyaltypoints.loyaltypoints.repository.CustomerRepository;
import com.loyaltypoints.loyaltypoints.repository.PointsLedgerRepository;
import com.loyaltypoints.loyaltypoints.repository.PurchaseRepository;
import com.loyaltypoints.loyaltypoints.repository.TierRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final CustomerRepository customerRepository;
    private final PointsLedgerRepository pointsLedgerRepository;
    private final TierRepository tierRepository;

    public PurchaseService(
            PurchaseRepository purchaseRepository,
            CustomerRepository customerRepository,
            PointsLedgerRepository pointsLedgerRepository,
            TierRepository tierRepository) {
        this.purchaseRepository = purchaseRepository;
        this.customerRepository = customerRepository;
        this.pointsLedgerRepository = pointsLedgerRepository;
        this.tierRepository = tierRepository;
    }

    @Transactional
    public Purchase processPurchase(Long customerId, Double amount) {

        if (amount == null || amount <= 0) {
            throw new RuntimeException("Purchase amount must be greater than zero");
        }

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        double discountPercentage = 0.0;

        if (customer.getTier() != null) {
            discountPercentage = customer.getTier().getDiscountPercentage();
        }

        double discountAmount = amount * discountPercentage / 100.0;
        double finalAmount = amount - discountAmount;

        int pointsEarned = (int) (finalAmount / 100.0);

        Purchase purchase = new Purchase();
        purchase.setCustomer(customer);
        purchase.setPurchaseAmount(amount);
        purchase.setDiscountAmount(discountAmount);
        purchase.setFinalAmount(finalAmount);
        purchase.setPointsEarned(pointsEarned);

        Purchase savedPurchase = purchaseRepository.save(purchase);

        customer.setPointsBalance(
                customer.getPointsBalance() + pointsEarned
        );

        updateCustomerTier(customer);

        customerRepository.save(customer);

        if (pointsEarned > 0) {
            PointsLedger ledger = new PointsLedger();
            ledger.setCustomer(customer);
            ledger.setPurchase(savedPurchase);
            ledger.setTransactionType(
                    PointsLedger.TransactionType.EARNED
            );
            ledger.setPoints(pointsEarned);
            ledger.setDescription("Points earned from purchase");

            pointsLedgerRepository.save(ledger);
        }

        return savedPurchase;
    }

    private void updateCustomerTier(Customer customer) {

        List<Tier> tiers = tierRepository.findAll();

        Tier eligibleTier = null;

        for (Tier tier : tiers) {
            if (customer.getPointsBalance() >= tier.getMinimumPoints()) {
                if (eligibleTier == null ||
                        tier.getMinimumPoints() > eligibleTier.getMinimumPoints()) {
                    eligibleTier = tier;
                }
            }
        }

        customer.setTier(eligibleTier);
    }

    public List<Purchase> getAllPurchases() {
        return purchaseRepository.findAll();
    }

    public Purchase getPurchaseById(Long id) {
        return purchaseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Purchase not found"));
    }

    public List<Purchase> getPurchasesByCustomer(Long customerId) {
        return purchaseRepository.findByCustomerIdOrderByPurchaseDateDesc(customerId);
    }
}