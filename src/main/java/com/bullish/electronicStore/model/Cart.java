package com.bullish.electronicStore.model;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Setter
@NoArgsConstructor
public class Cart {

    private Map<Integer, OrderItem> orderItems = new ConcurrentHashMap<>();

    private Double totalOriginalPrice = 0.0;
    private Double totalDiscount = 0.0;
    private Double totalFinalPrice = 0.0;

}
