package com.loyaltypoints.loyaltypoints.controller;

import com.loyaltypoints.loyaltypoints.service.PointsRedemptionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/redemption")
public class PointsRedemptionController {

    private final PointsRedemptionService pointsRedemptionService;

    public PointsRedemptionController(
            PointsRedemptionService pointsRedemptionService) {
        this.pointsRedemptionService = pointsRedemptionService;
    }

    @PostMapping("/{customerId}")
    public String redeemPoints(
            @PathVariable Long customerId,
            @RequestParam Integer points) {
        return pointsRedemptionService.redeemPoints(customerId, points);
    }
}