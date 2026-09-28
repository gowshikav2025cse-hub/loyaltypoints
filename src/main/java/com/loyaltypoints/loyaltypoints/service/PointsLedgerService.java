package com.loyaltypoints.loyaltypoints.service;

import com.loyaltypoints.loyaltypoints.entity.PointsLedger;
import com.loyaltypoints.loyaltypoints.repository.PointsLedgerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PointsLedgerService {

    private final PointsLedgerRepository pointsLedgerRepository;

    public PointsLedgerService(PointsLedgerRepository pointsLedgerRepository) {
        this.pointsLedgerRepository = pointsLedgerRepository;
    }

    public List<PointsLedger> getAllTransactions() {
        return pointsLedgerRepository.findAll();
    }

    public List<PointsLedger> getTransactionsByCustomer(Long customerId) {
        return pointsLedgerRepository.findByCustomerIdOrderByTransactionDateDesc(customerId);
    }
}