package com.loyaltypoints.loyaltypoints.repository;

import com.loyaltypoints.loyaltypoints.entity.Tier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TierRepository extends JpaRepository<Tier, Long> {

    Optional<Tier> findByName(String name);

    Optional<Tier> findFirstByOrderByMinimumPointsDesc();
}