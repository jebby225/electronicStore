package com.bullish.electronicStore;

import com.bullish.electronicStore.enums.DiscountAmountUnit;
import com.bullish.electronicStore.enums.DiscountType;
import com.bullish.electronicStore.model.OrderItem;
import com.bullish.electronicStore.model.Product;
import com.bullish.electronicStore.model.ProductDiscount;
import com.bullish.electronicStore.service.ProductDiscountService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {ElectronicStoreApplication.class })
public class ProductDiscountServiceTest {

    @Autowired
    private ProductDiscountService productDiscountService;

    Product product = new Product("cm1", "coffee Machine", "coffeemachine_1.jpg", 145.0, "coffee machine");
    OrderItem orderItem = new OrderItem(product.getCode(), product.getName(), 11, product.getPrice());

    /*
     * All 50% off                ProductDiscount(DiscountType.BUY_ANY_WITH_TOTAL_DISCOUNT, 50.0, DiscountAmountUnit.PERCENT)
     * Total 5 off                ProductDiscount(DiscountType.BUY_ANY_WITH_TOTAL_DISCOUNT, 5.0, DiscountAmountUnit.AMOUNT)
     * Buy 1 get 3 20% off        ProductDiscount(DiscountType.BUY_X_GET_Y_WITH_Z_DISCOUNT, 1, 3, 20.0, DiscountAmountUnit.PERCENT)
     * Buy 2 get 1 free           ProductDiscount(DiscountType.BUY_X_GET_Y_WITH_Z_DISCOUNT, 2, 1, 100.0, DiscountAmountUnit.PERCENT)
     * Each $10 off               ProductDiscount(DiscountType.BUY_ANY_WITH_UNIT_DISCOUNT, 10.0, DiscountAmountUnit.AMOUNT);
     * Buy 3 get $10 off          ProductDiscount(DiscountType.BUY_X_WITH_Z_DISCOUNT, 3, 10.0, DiscountAmountUnit.AMOUNT);
     * Buy 3 get 10% off          ProductDiscount(DiscountType.BUY_X_WITH_Z_DISCOUNT, 3, 10.0, DiscountAmountUnit.PERCENT);
     * Buy every $100 $10 off     ProductDiscount(DiscountType.BUY_AMOUNT_WITH_DISCOUNT, 100.0, 10.0, DiscountAmountUnit.AMOUNT);
     * Buy over $100 10% off      ProductDiscount(DiscountType.BUY_AMOUNT_WITH_DISCOUNT, 100.0, 10.0, DiscountAmountUnit.PERCENT);
     */

    @Test
    public void testCalculateTotalProductDiscount_all50PercentOff() {
        ProductDiscount pdtDiscount = new ProductDiscount(DiscountType.BUY_ANY_WITH_TOTAL_DISCOUNT, 50.0, DiscountAmountUnit.PERCENT);
        double discount = productDiscountService.calculateTotalProductDiscount(pdtDiscount, orderItem);

        assertEquals(discount, orderItem.getQuantity() * orderItem.getPrice() * 0.5);
    }

    @Test
    public void testCalculateTotalProductDiscount_total5AmountOff() {
        ProductDiscount pdtDiscount = new ProductDiscount(DiscountType.BUY_ANY_WITH_TOTAL_DISCOUNT, 5.0, DiscountAmountUnit.AMOUNT);
        double discount = productDiscountService.calculateTotalProductDiscount(pdtDiscount, orderItem);

        assertEquals(discount, pdtDiscount.getDiscountAmount());
    }

    @Test
    public void testCalculateTotalProductDiscount_buy1Get3With20PercentOff() {
        ProductDiscount pdtDiscount = new ProductDiscount(
                DiscountType.BUY_X_GET_Y_WITH_Z_DISCOUNT,
                1, 3, 20.0, DiscountAmountUnit.PERCENT);
        double discount = productDiscountService.calculateTotalProductDiscount(pdtDiscount, orderItem);

        assertEquals(discount, 8 * orderItem.getPrice() * 0.20);
    }

    @Test
    public void testCalculateTotalProductDiscount_buy2Get1Off() {
        ProductDiscount pdtDiscount = new ProductDiscount(
                DiscountType.BUY_X_GET_Y_WITH_Z_DISCOUNT,
                2, 1, 100.0, DiscountAmountUnit.PERCENT);
        double discount = productDiscountService.calculateTotalProductDiscount(pdtDiscount, orderItem);

        assertEquals(discount, 3 * orderItem.getPrice());
    }

    @Test
    public void testCalculateTotalProductDiscount_all10AmountOff() {
        ProductDiscount pdtDiscount = new ProductDiscount(
                DiscountType.BUY_ANY_WITH_UNIT_DISCOUNT, 10.0, DiscountAmountUnit.AMOUNT);
        double discount = productDiscountService.calculateTotalProductDiscount(pdtDiscount, orderItem);

        assertEquals(discount, orderItem.getQuantity() * 10);
    }



    @Test
    public void testCalculateTotalProductDiscount_Buy3Get10AmtOffTotal() {
        ProductDiscount pdtDiscount = new ProductDiscount(
                DiscountType.BUY_X_WITH_Z_DISCOUNT, 3, 10.0, DiscountAmountUnit.AMOUNT);
        double discount = productDiscountService.calculateTotalProductDiscount(pdtDiscount, orderItem);

        assertEquals(discount, 3 * 10.0);
    }

    @Test
    public void testCalculateTotalProductDiscount_Buy3Get10PercentOffTotal() {
        ProductDiscount pdtDiscount = new ProductDiscount(
                DiscountType.BUY_X_WITH_Z_DISCOUNT, 3, 10.0, DiscountAmountUnit.PERCENT);
        double discount = productDiscountService.calculateTotalProductDiscount(pdtDiscount, orderItem);

        assertEquals(discount, 3 * orderItem.getPrice() * 0.1);
    }

    @Test
    public void testCalculateTotalProductDiscount_BuyEvery100_10AmountOff() {
        ProductDiscount pdtDiscount = new ProductDiscount(
                DiscountType.BUY_AMOUNT_WITH_DISCOUNT, 100.0, 10.0, DiscountAmountUnit.AMOUNT);
        double discount = productDiscountService.calculateTotalProductDiscount(pdtDiscount, orderItem);

        assertEquals(discount, (int) Math.floor(orderItem.getPrice() * orderItem.getQuantity() / 100.0) * 10.0);
    }

    @Test
    public void testCalculateTotalProductDiscount_BuyOver100_10PercentOff() {
        ProductDiscount pdtDiscount = new ProductDiscount(
                DiscountType.BUY_AMOUNT_WITH_DISCOUNT, 100.0, 10.0, DiscountAmountUnit.PERCENT);
        double discount = productDiscountService.calculateTotalProductDiscount(pdtDiscount, orderItem);

        assertEquals(discount, orderItem.getPrice() * orderItem.getQuantity() * 0.1);
    }
}