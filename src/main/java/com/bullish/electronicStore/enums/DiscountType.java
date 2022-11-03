package com.bullish.electronicStore.enums;

public enum DiscountType {
    BUY_X_GET_Y_WITH_Z_DISCOUNT,    // e.g. Buy 3 get next 1 10% off, Buy 3 Get 1 free
    BUY_X_WITH_Z_DISCOUNT,          // e.g. Buy 3 get $10 off total, Buy 3 get 10% off total
    BUY_ANY_WITH_TOTAL_DISCOUNT,    // e.g. All 50% Off, $10 off total
    BUY_ANY_WITH_UNIT_DISCOUNT,     // e.g. Each $10 off
    BUY_AMOUNT_WITH_DISCOUNT        // e.g. Buy every $100 $10 off, Over $100 10% off
}
