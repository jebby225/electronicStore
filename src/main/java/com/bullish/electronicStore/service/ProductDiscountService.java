package com.bullish.electronicStore.service;

import com.bullish.electronicStore.model.OrderItem;
import com.bullish.electronicStore.model.ProductDiscount;
import org.springframework.stereotype.Service;

@Service
public class ProductDiscountService {

    public double calculateTotalProductDiscount(ProductDiscount pdtDiscount, OrderItem orderItem) {
        int discountSetCount = pdtDiscount.getUnitToPurchase() + pdtDiscount.getUnitWithDiscount();
        int discountSetsInOrder = (int) Math.floor(orderItem.getQuantity() / discountSetCount);
        int itemsToDiscount = discountSetsInOrder * discountSetCount; // round down to get number of items meeting
        // requirement
        int unitsWithDiscount = 0;

        if (orderItem.getQuantity() >= discountSetCount) {
            // get amounts for sets of items meeting the criteria completely
            // (for example, if the discount is buy 2 get one 1 free and we have 10 items in
            // cart, then below should multiply 3 times)

            // if pdtDiscount.UnitWithDiscount, we are applying a rule that discount will
            // apply to the full set
            // if the criteria is met
            // ie.
            // buy 5 get 20% off
            // if set has 5 items, apply 20% off to all 5 items
            if (pdtDiscount.getUnitWithDiscount() == 0) {
                unitsWithDiscount += pdtDiscount.getUnitToPurchase() * discountSetsInOrder;
            } else {
                unitsWithDiscount += pdtDiscount.getUnitWithDiscount() * discountSetsInOrder;
            }
        }

        // adding the remainders
        int remainingItemCount = orderItem.getQuantity() - itemsToDiscount;

        // if pdtDiscount.getUnitWithDiscount() > 0, it means discounts only applied to
        // full set,
        // therefore we don't need to worry about leftovers
        if (remainingItemCount > 0 && pdtDiscount.getUnitWithDiscount() > 0
                && pdtDiscount.getUnitToPurchase() < remainingItemCount) {
            unitsWithDiscount += remainingItemCount - pdtDiscount.getUnitToPurchase();
        }

        if (pdtDiscount.isPercent())
            return ((orderItem.getPrice() * pdtDiscount.getDiscountAmount()) / 100)
                    * unitsWithDiscount;
        else
            return pdtDiscount.getDiscountAmount() * unitsWithDiscount;
    }


// different type of discounts
// const discount = new Discount(20, true, 1, 2, "buy 1 get next 2 20% off");
// const discount = new Discount(50, true, 0, 1, "50% off");
// const discount = new Discount(100, true, 2, 1, "buy 2 get 1 off");
//const discount = new Discount(5, false, 0, 1, "10 off");



}
