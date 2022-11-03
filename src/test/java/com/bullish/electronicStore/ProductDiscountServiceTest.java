package com.bullish.electronicStore;

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

    Product product = new Product("cm1", "coffee Machine", "coffeemachine_1.jpg", 100.0, "coffee machine");
    OrderItem orderItem = new OrderItem(product.getCode(), product.getName(), 10, product.getPrice());

    @Test
    public void testCalculateTotalProductDiscount_all50PercentOff() {
        ProductDiscount pdtDiscount = new ProductDiscount(50.0, true, 0, 1, "get 50% off all");
        double discount = productDiscountService.calculateTotalProductDiscount(pdtDiscount, orderItem);

        assertEquals(discount, 10 * orderItem.getPrice() * 0.5);

    }

    @Test
    public void testCalculateTotalProductDiscount_buy1Get2With20PercentOff() {
        ProductDiscount pdtDiscount = new ProductDiscount(20.0, true, 1, 3, "buy 1 get next 3 20% off");
        double discount = productDiscountService.calculateTotalProductDiscount(pdtDiscount, orderItem);

        assertEquals(discount, 7 * orderItem.getPrice() * 0.20);

    }

    @Test
    public void testCalculateTotalProductDiscount_buy2Get1Off() {
        ProductDiscount pdtDiscount = new ProductDiscount(100.0, true, 2, 1, "buy 2 get 1 off");
        double discount = productDiscountService.calculateTotalProductDiscount(pdtDiscount, orderItem);

        assertEquals(discount, 3 * orderItem.getPrice());

    }

    @Test
    public void testCalculateTotalProductDiscount_all10AmountOff() {
        ProductDiscount pdtDiscount = new ProductDiscount(10.0, false, 0, 1, "10 off");
        double discount = productDiscountService.calculateTotalProductDiscount(pdtDiscount, orderItem);

        assertEquals(discount, 10 * 10);
    }

    @Test
    public void testCalculateTotalProductDiscount_fullSet10PercentOff() {
        ProductDiscount pdtDiscount = new ProductDiscount(10.0, false, 10, 0, "buy 10 get 10% off");
        double discount = productDiscountService.calculateTotalProductDiscount(pdtDiscount, orderItem);

        assertEquals(discount, 10 * orderItem.getPrice() * 0.10);
    }

    @Test
    public void testCalculateTotalProductDiscount_1Set10PercentOff() {
        ProductDiscount pdtDiscount = new ProductDiscount(10.0, false, 6, 0, "buy 10 get 10% off");
        double discount = productDiscountService.calculateTotalProductDiscount(pdtDiscount, orderItem);

        assertEquals(discount, 6 * orderItem.getPrice() * 0.10);
    }
}