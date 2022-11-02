package com.bullish.electronicStore.entity;

import java.util.ArrayList;
import java.util.List;

public class CheckoutReceipt {

    private List<CheckoutItem> checkoutItemList = new ArrayList<>();
    private Double totalOriginalPrice = 0.0;
    private Double totalDiscount = 0.0;
    private Double totalFinalPrice = 0.0;

    public CheckoutReceipt(List<CheckoutItem> checkoutItemList) {
        this.checkoutItemList = checkoutItemList;
    }

    public Double getTotalOriginalPrice() {
        return totalOriginalPrice;
    }

    public void setTotalOriginalPrice(Double totalOriginalPrice) {
        this.totalOriginalPrice = totalOriginalPrice;
    }

    public Double getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(Double totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public Double getTotalFinalPrice() {
        return totalFinalPrice;
    }

    public void setTotalFinalPrice(Double totalFinalPrice) {
        this.totalFinalPrice = totalFinalPrice;
    }


}
