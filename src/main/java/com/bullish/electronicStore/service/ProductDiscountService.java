package com.bullish.electronicStore.service;

import com.bullish.electronicStore.enums.DiscountAmountUnit;
import com.bullish.electronicStore.enums.DiscountType;
import com.bullish.electronicStore.model.OrderItem;
import com.bullish.electronicStore.model.ProductDiscount;
import org.springframework.stereotype.Service;

@Service
public class ProductDiscountService {

    public double calculateTotalProductDiscount(ProductDiscount pdtDiscount, OrderItem orderItem) {
        switch (pdtDiscount.getDiscountType()) {
            case BUY_X_GET_Y_WITH_Z_DISCOUNT:
                return calculateTotalProductDiscount_buy_x_get_y_with_z_discount(pdtDiscount, orderItem);
            case BUY_X_WITH_Z_DISCOUNT:
                return calculateTotalProductDiscount_buy_x_with_z_discount(pdtDiscount, orderItem);
            case BUY_AMOUNT_WITH_DISCOUNT:
                return calculateTotalProductDiscount_buy_amount_with_discount(pdtDiscount, orderItem);
            case BUY_ANY_WITH_TOTAL_DISCOUNT:
            case BUY_ANY_WITH_UNIT_DISCOUNT:
                return calculateTotalProductDiscount_buy_any_with_discount(pdtDiscount, orderItem);
            default:
                return 0.0;
        }
    }

    private double calculateTotalProductDiscount_buy_x_get_y_with_z_discount(ProductDiscount pdtDiscount, OrderItem orderItem) {
        int discountSetCount = pdtDiscount.getPurchaseUnit() + pdtDiscount.getUnitWithDiscount();
        int discountSetsInOrder = (int) Math.floor(orderItem.getQuantity() / discountSetCount);
        int itemsToDiscount = discountSetsInOrder * discountSetCount; // round down to get number of items meeting requirement
        int unitsWithDiscount = 0;

        if (orderItem.getQuantity() >= discountSetCount) {
            // get amounts for sets of items meeting the criteria completely
            // (for example, if the discount is buy 2 get one 1 free and we have 10 items in
            // cart, then below should multiply 3 times)

            // if pdtDiscount.UnitWithDiscount, we are applying a rule that discount will apply to the full set if the criteria is met
            // i.e. buy 5 get 20% off; if set has 5 items, apply 20% off to all 5 items
            if (pdtDiscount.getUnitWithDiscount() == 0) {
                unitsWithDiscount += pdtDiscount.getPurchaseUnit() * discountSetsInOrder;
            } else {
                unitsWithDiscount += pdtDiscount.getUnitWithDiscount() * discountSetsInOrder;
            }
        }

        // adding the remainders
        int remainingItemCount = orderItem.getQuantity() - itemsToDiscount;

        // if pdtDiscount.getUnitWithDiscount() > 0, it means discounts only applied to full set;
        // therefore we don't need to worry about leftovers
        if (remainingItemCount > 0 && pdtDiscount.getUnitWithDiscount() > 0
                && pdtDiscount.getPurchaseUnit() < remainingItemCount) {
            unitsWithDiscount += remainingItemCount - pdtDiscount.getPurchaseUnit();
        }

        if (pdtDiscount.getDiscountAmountUnit() == DiscountAmountUnit.PERCENT)
            return ((orderItem.getPrice() * pdtDiscount.getDiscountAmount()) / 100)
                    * unitsWithDiscount;
        else
            return pdtDiscount.getDiscountAmount() * unitsWithDiscount;
    }

    private double calculateTotalProductDiscount_buy_x_with_z_discount(ProductDiscount pdtDiscount, OrderItem orderItem) {
        int discountSetsInOrder = (int) Math.floor(orderItem.getQuantity() / pdtDiscount.getPurchaseUnit());
            if (pdtDiscount.getDiscountAmountUnit() == DiscountAmountUnit.PERCENT)
                return ((orderItem.getPrice() * pdtDiscount.getDiscountAmount()) / 100) * discountSetsInOrder;
            else
                return pdtDiscount.getDiscountAmount() * discountSetsInOrder;
    }

    private double calculateTotalProductDiscount_buy_amount_with_discount(ProductDiscount pdtDiscount, OrderItem orderItem) {
        double totalPrice = orderItem.getPrice() * orderItem.getQuantity();

        if (pdtDiscount.getDiscountAmountUnit() == DiscountAmountUnit.PERCENT) {
            if(totalPrice >= pdtDiscount.getPurchaseAmount())
                return totalPrice * pdtDiscount.getDiscountAmount() / 100;
            else
                return 0.0;
        }
        else {
            int discountSetsInOrder = (int) Math.floor(totalPrice / pdtDiscount.getPurchaseAmount());
            return pdtDiscount.getDiscountAmount() * discountSetsInOrder;
        }
    }

    private double calculateTotalProductDiscount_buy_any_with_discount(ProductDiscount pdtDiscount, OrderItem orderItem) {
        if (pdtDiscount.getDiscountAmountUnit() == DiscountAmountUnit.PERCENT)
            return orderItem.getPrice() * orderItem.getQuantity() * pdtDiscount.getDiscountAmount() / 100;
        else {
            if(pdtDiscount.getDiscountType() == DiscountType.BUY_ANY_WITH_TOTAL_DISCOUNT)
                return pdtDiscount.getDiscountAmount();
            else
                return pdtDiscount.getDiscountAmount() * orderItem.getQuantity();
        }
    }

}
