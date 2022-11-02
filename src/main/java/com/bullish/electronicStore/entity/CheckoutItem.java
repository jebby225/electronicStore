package com.bullish.electronicStore.entity;

public class CheckoutItem {
    private String productCode;
    private String productName;
    private int quantity;
    private Double price;

    private String appliedDiscount;

    public CheckoutItem(String productCode, String productName, int quantity, Double price, String appliedDiscount) {
        this.productCode = productCode;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.appliedDiscount = appliedDiscount;
    }




}
