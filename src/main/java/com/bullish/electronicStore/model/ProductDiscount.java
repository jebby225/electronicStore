package com.bullish.electronicStore.model;

import com.bullish.electronicStore.enums.DiscountAmountUnit;
import com.bullish.electronicStore.enums.DiscountType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ProductDiscount {

    private DiscountType discountType;
    private Double discountAmount = 0.0;
    private DiscountAmountUnit discountAmountUnit;
    private Integer purchaseUnit = 0;
    private Double purchaseAmount = 0.0;
    private Integer unitWithDiscount = 0;
    private String description;


    // BUY_X_GET_Y_WITH_Z_DISCOUNT
    public ProductDiscount(DiscountType discountType, Integer purchaseUnit, Integer unitWithDiscount, Double discountAmount, DiscountAmountUnit discountAmountUnit) {
        this.discountType = discountType;
        this.discountAmount = discountAmount;
        this.discountAmountUnit = discountAmountUnit;
        this.purchaseUnit = purchaseUnit;
        this.unitWithDiscount = unitWithDiscount;
        this.description = discountType.name();
    }

    // BUY_X_WITH_Z_DISCOUNT
    public ProductDiscount(DiscountType discountType, Integer purchaseUnit, Double discountAmount, DiscountAmountUnit discountAmountUnit) {
        this.discountType = discountType;
        this.discountAmount = discountAmount;
        this.discountAmountUnit = discountAmountUnit;
        this.purchaseUnit = purchaseUnit;
        this.description = discountType.name();
    }

    // BUY_AMOUNT_WITH_DISCOUNT
    public ProductDiscount(DiscountType discountType, Double purchaseAmount, Double discountAmount, DiscountAmountUnit discountAmountUnit) {
        this.discountType = discountType;
        this.discountAmount = discountAmount;
        this.discountAmountUnit = discountAmountUnit;
        this.purchaseAmount = purchaseAmount;
        this.description = discountType.name();
    }

    // BUY_ANY_WITH_DISCOUNT
    public ProductDiscount(DiscountType discountType, double discountAmount, DiscountAmountUnit discountAmountUnit) {
        this.discountType = discountType;
        this.discountAmount = discountAmount;
        this.discountAmountUnit = discountAmountUnit;
        this.description = discountType.name();
    }
}