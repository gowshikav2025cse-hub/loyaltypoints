package com.loyaltypoints.loyaltypoints.repository;

import com.loyaltypoints.loyaltypoints.entity.PointsLedger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PointsLedgerRepository extends JpaRepository<PointsLedger, Long> {

    List<PointsLedger> findByCustomerIdOrderByTransactionDateDesc(Long customerId);
}