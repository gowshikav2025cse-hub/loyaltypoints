package com.loyaltypoints.loyaltypoints.controller;

import com.loyaltypoints.loyaltypoints.entity.Tier;
import com.loyaltypoints.loyaltypoints.service.TierService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tiers")
public class TierController {

    private final TierService tierService;

    public TierController(TierService tierService) {
        this.tierService = tierService;
    }

    @PostMapping
    public Tier createTier(@RequestBody Tier tier) {
        return tierService.createTier(tier);
    }

    @GetMapping
    public List<Tier> getAllTiers() {
        return tierService.getAllTiers();
    }

    @GetMapping("/{id}")
    public Tier getTierById(@PathVariable Long id) {
        return tierService.getTierById(id);
    }

    @PutMapping("/{id}")
    public Tier updateTier(
            @PathVariable Long id,
            @RequestBody Tier tier) {
        return tierService.updateTier(id, tier);
    }

    @DeleteMapping("/{id}")
    public String deleteTier(@PathVariable Long id) {
        tierService.deleteTier(id);
        return "Tier deleted successfully";
    }
}