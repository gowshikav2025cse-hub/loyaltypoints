package com.loyaltypoints.loyaltypoints.service;

import com.loyaltypoints.loyaltypoints.entity.Tier;
import com.loyaltypoints.loyaltypoints.repository.TierRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TierService {

    private final TierRepository tierRepository;

    public TierService(TierRepository tierRepository) {
        this.tierRepository = tierRepository;
    }

    public Tier createTier(Tier tier) {
        return tierRepository.save(tier);
    }

    public List<Tier> getAllTiers() {
        return tierRepository.findAll();
    }

    public Tier getTierById(Long id) {
        return tierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tier not found"));
    }

    public Tier updateTier(Long id, Tier updatedTier) {
        Tier tier = getTierById(id);

        tier.setName(updatedTier.getName());
        tier.setMinimumPoints(updatedTier.getMinimumPoints());
        tier.setDiscountPercentage(updatedTier.getDiscountPercentage());

        return tierRepository.save(tier);
    }

    public void deleteTier(Long id) {
        Tier tier = getTierById(id);
        tierRepository.delete(tier);
    }
}