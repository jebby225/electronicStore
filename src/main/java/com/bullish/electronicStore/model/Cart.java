package com.bullish.electronicStore.model;
import com.bullish.electronicStore.converter.OrderItemsConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Convert;
import java.util.*;

@Getter
@Setter
public class Cart {

    @Convert(converter = OrderItemsConverter.class)
    private Map<Integer, OrderItem> orderItems = new HashMap<>();

    private Double totalOriginalPrice = 0.0;
    private Double totalDiscount = 0.0;
    private Double totalFinalPrice = 0.0;

    public Cart() {}


}
