package com.bullish.electronicStore.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderItem {
    private String productCode;
    private String productName;
    private Integer quantity;
    private Double price;
    private String appliedDiscount;
    private Double discount = 0.0;
    private Double subTotal;

    public OrderItem() {}
    public OrderItem(String productCode, String productName, Integer quantity, Double price) {
        this.productCode = productCode;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }




}
