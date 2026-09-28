
package com.loyaltypoints.loyaltypoints.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tiers")
public class Tier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private Integer minimumPoints;

    @Column(nullable = false)
    private Double discountPercentage;

    public Tier() {
    }

    public Tier(String name, Integer minimumPoints, Double discountPercentage) {
        this.name = name;
        this.minimumPoints = minimumPoints;
        this.discountPercentage = discountPercentage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMinimumPoints() {
        return minimumPoints;
    }

    public void setMinimumPoints(Integer minimumPoints) {
        this.minimumPoints = minimumPoints;
    }

    public Double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(Double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }
}