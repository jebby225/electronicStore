package com.bullish.electronicStore.service;

import com.bullish.electronicStore.model.OrderItem;
import com.bullish.electronicStore.model.ProductDiscount;
import org.springframework.stereotype.Service;

@Service
public class ProductDiscountService {

    public double calculateTotalProductDiscount(ProductDiscount pdtDiscount, OrderItem orderItem) {
        int discountSetCount = pdtDiscount.getUnitToPurchase() + pdtDiscount.getUnitWithDiscount();
        int itemsToDiscount = (int)Math.floor(orderItem.getQuantity() / discountSetCount) * discountSetCount; // round down to get number of items meeting requirement

        double totalPrice = 0.0;
        double totalDiscount = 0.0;
        if (orderItem.getQuantity() >= discountSetCount) {
            // get amounts for sets of items meeting the criteria completely
            // (for example, if the discount is buy 2 get one 1 free and we have 10 items in cart, then below should iterate 3 times)
            for(int i = 0; i < itemsToDiscount / discountSetCount; i++) {
                totalPrice += pdtDiscount.getUnitToPurchase() * orderItem.getPrice();

                if(pdtDiscount.isPercent())
                    totalDiscount += ((orderItem.getPrice() * pdtDiscount.getDiscountAmount()) / 100) * pdtDiscount.getUnitWithDiscount();
                else
                    totalDiscount += pdtDiscount.getDiscountAmount() * pdtDiscount.getUnitWithDiscount();
            }
        }

        // adding the remainders
        int remainingItemCount = orderItem.getQuantity() - itemsToDiscount;
        if (remainingItemCount > 0) {
            if(pdtDiscount.getUnitToPurchase() < remainingItemCount) {
                remainingItemCount -= pdtDiscount.getUnitToPurchase();

                if (pdtDiscount.isPercent())
                    totalDiscount += ((orderItem.getPrice() * pdtDiscount.getDiscountAmount()) / 100) * remainingItemCount;
                else
                    totalDiscount += pdtDiscount.getDiscountAmount() * remainingItemCount;
            }
        }
        return totalDiscount;
    }


// different type of discounts
// const discount = new Discount(20, true, 1, 2, "buy 1 get next 2 20% off");
// const discount = new Discount(50, true, 0, 1, "50% off");
// const discount = new Discount(100, true, 2, 1, "buy 2 get 1 off");
//const discount = new Discount(5, false, 0, 1, "10 off");



}
