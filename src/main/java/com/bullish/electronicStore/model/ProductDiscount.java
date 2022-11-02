package com.bullish.electronicStore.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductDiscount {
    private Double discountAmount;
    private boolean isPercent;

    private Integer unitToPurchase;
    private Integer unitWithDiscount;

    private String description;

    public ProductDiscount() {}

    public ProductDiscount(Double discountAmount, boolean isPercent, Integer unitToPurchase, Integer unitWithDiscount, String description) {
        this.discountAmount = discountAmount;
        this.isPercent = isPercent;
        this.unitToPurchase = unitToPurchase;
        this.unitWithDiscount = unitWithDiscount;
        this.description = description;
    }
}
