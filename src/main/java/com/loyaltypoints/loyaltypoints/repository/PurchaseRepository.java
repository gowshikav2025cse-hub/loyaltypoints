package com.loyaltypoints.loyaltypoints.repository;

import com.loyaltypoints.loyaltypoints.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    List<Purchase> findByCustomerIdOrderByPurchaseDateDesc(Long customerId);
}