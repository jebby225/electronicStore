package com.bullish.electronicStore;

import com.bullish.electronicStore.enums.DiscountAmountUnit;
import com.bullish.electronicStore.enums.DiscountType;
import com.bullish.electronicStore.enums.UserRoles;
import com.bullish.electronicStore.exception.CustomException;
import com.bullish.electronicStore.model.*;
import com.bullish.electronicStore.repository.ProductRepository;
import com.bullish.electronicStore.repository.UserRepository;
import com.bullish.electronicStore.service.CartService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest(classes = { ElectronicStoreApplication.class })
public class CartServiceTest {

    @Autowired private CartService cartService;
    @Autowired private UserRepository userRepository;
    @Autowired private ProductRepository productRepository;

    User alice = new User("alice", "alice@bullish.com", UserRoles.CUSTOMER);
    User bob = new User("bob", "bob@bullish.com", UserRoles.CUSTOMER);
    Product coffeeMachine = new Product("cm1", "Coffee Machine", "coffeemachine.jpg", 200.0, "coffee machine");
    Product blender = new Product("b2", "Blender", "blender.jpg", 120.0, "juice blender");

    int alice_id;
    int coffeeMachine_id;
    int blender_id;

    @BeforeEach
    public void init() {
        userRepository.deleteAll();
        productRepository.deleteAll();

        alice = userRepository.save(alice);
        bob = userRepository.save(bob);
        alice_id = alice.getId();

        coffeeMachine = productRepository.save(coffeeMachine);
        blender = productRepository.save(blender);
        coffeeMachine_id = coffeeMachine.getId();
        blender_id = blender.getId();
    }

    @Test
    public void testUpdateCartService_AssertingOrderItemLists() {

        // Start with empty cart (with no order items)
        Cart cart = userRepository.findById(alice_id).getCart();
        Assertions
                .assertThat(cart)
                .isNotNull()
                .hasFieldOrPropertyWithValue("orderItems", new HashMap<>());

        // Add 4 blenders to basket
        cart = cartService.updateCart(alice_id, blender_id, 4);
        Assertions
                .assertThat(cart.getOrderItems())
                .isNotNull();

        assertThat(cart.getOrderItems())
                .isNotEmpty()
                .hasSize(1)
                .containsKey(blender_id)
                .doesNotContainKeys(coffeeMachine_id);

        assertThat(cart.getOrderItems().get(blender_id))
                .isNotNull()
                .hasFieldOrPropertyWithValue("productCode", "b2")
                .hasFieldOrPropertyWithValue("productName", "Blender")
                .hasFieldOrPropertyWithValue("quantity", 4)
                .hasFieldOrPropertyWithValue("price", 120.0);

        // Add 2 coffee machine to basket
        cart = cartService.updateCart(alice_id, coffeeMachine_id, 2);
        Assertions
                .assertThat(cart.getOrderItems())
                .isNotNull();

        assertThat(cart.getOrderItems())
                .isNotEmpty()
                .hasSize(2)
                .containsKey(coffeeMachine_id)
                .containsKey(blender_id);

        assertThat(cart.getOrderItems().get(coffeeMachine_id))
                .isNotNull()
                .hasFieldOrPropertyWithValue("productCode", "cm1")
                .hasFieldOrPropertyWithValue("productName", "Coffee Machine")
                .hasFieldOrPropertyWithValue("quantity", 2)
                .hasFieldOrPropertyWithValue("price", 200.0);

        // Remove 1 blender from basket -> now only 3 blender in basket
        cart = cartService.updateCart(alice_id, blender_id, 3);
        assertThat(cart.getOrderItems().get(blender_id))
                .isNotNull()
                .hasFieldOrPropertyWithValue("productCode", "b2")
                .hasFieldOrPropertyWithValue("quantity", 3);

        // Remove all coffee machine from basket, now only one order item (blender) left in basket
        cart = cartService.updateCart(alice_id, coffeeMachine_id, 0);
        assertThat(cart.getOrderItems())
                .isNotEmpty()
                .hasSize(1)
                .containsKey(blender_id)
                .doesNotContainKeys(coffeeMachine_id);
    }

    @Test
    public void testUpdateCartService_AssertingPrices() {

        // Start with empty cart (with no order items)
        Cart cart = userRepository.findById(alice_id).getCart();
        Assertions
                .assertThat(cart)
                .isNotNull()
                .hasFieldOrPropertyWithValue("orderItems", new HashMap<>())
                .hasFieldOrPropertyWithValue("totalOriginalPrice", 0.0)
                .hasFieldOrPropertyWithValue("totalDiscount", 0.0)
                .hasFieldOrPropertyWithValue("totalFinalPrice", 0.0);

        // Add 4 blenders to basket with no discount
        cart = cartService.updateCart(alice_id, blender_id, 4);
             assertThat(cart.getOrderItems().get(blender_id))
                .isNotNull()
                .hasFieldOrPropertyWithValue("productCode", "b2")
                .hasFieldOrPropertyWithValue("appliedDiscount", null)
                .hasFieldOrPropertyWithValue("discount", 0.0)
                .hasFieldOrPropertyWithValue("subTotal", 480.0);

            assertThat(cart)
                .hasFieldOrPropertyWithValue("totalOriginalPrice", 480.0)
                .hasFieldOrPropertyWithValue("totalDiscount", 0.0)
                .hasFieldOrPropertyWithValue("totalFinalPrice", 480.0);

        // Add 1 coffee machine to basket with no discount
        cart = cartService.updateCart(alice_id, coffeeMachine_id, 1);
        assertThat(cart.getOrderItems().get(coffeeMachine_id))
                .isNotNull()
                .hasFieldOrPropertyWithValue("productCode", "cm1")
                .hasFieldOrPropertyWithValue("appliedDiscount", null)
                .hasFieldOrPropertyWithValue("discount", 0.0)
                .hasFieldOrPropertyWithValue("subTotal", 200.0);

        assertThat(cart)
                .hasFieldOrPropertyWithValue("totalOriginalPrice", 680.0)
                .hasFieldOrPropertyWithValue("totalDiscount", 0.0)
                .hasFieldOrPropertyWithValue("totalFinalPrice", 680.0);

        // Apply discount to product
        coffeeMachine.setProductDiscount(
                new ProductDiscount(DiscountType.BUY_X_GET_Y_WITH_Z_DISCOUNT, 2, 1, 100.0, DiscountAmountUnit.PERCENT));
        productRepository.save(coffeeMachine);

        // Add 2 more coffee machine to basket with discount
        cart = cartService.updateCart(alice_id, coffeeMachine_id, 3);
        assertThat(cart.getOrderItems().get(coffeeMachine_id))
                .isNotNull()
                .hasFieldOrPropertyWithValue("productCode", "cm1")
                .hasFieldOrPropertyWithValue("appliedDiscount", "BUY_X_GET_Y_WITH_Z_DISCOUNT")
                .hasFieldOrPropertyWithValue("discount", 200.0)
                .hasFieldOrPropertyWithValue("subTotal", 400.0);
        assertThat(cart)
                .hasFieldOrPropertyWithValue("totalOriginalPrice", 1080.0)
                .hasFieldOrPropertyWithValue("totalDiscount", 200.0)
                .hasFieldOrPropertyWithValue("totalFinalPrice", 880.0);

        // Remove 1 coffee machine from basket - now the buy 2 get 1 free discount not applicable
        cart = cartService.updateCart(alice_id, coffeeMachine_id, 2);
        assertThat(cart.getOrderItems().get(coffeeMachine_id))
                .isNotNull()
                .hasFieldOrPropertyWithValue("productCode", "cm1")
                .hasFieldOrPropertyWithValue("appliedDiscount", "BUY_X_GET_Y_WITH_Z_DISCOUNT")
                .hasFieldOrPropertyWithValue("discount", 0.0)
                .hasFieldOrPropertyWithValue("subTotal", 400.0);
        assertThat(cart)
                .hasFieldOrPropertyWithValue("totalOriginalPrice", 880.0)
                .hasFieldOrPropertyWithValue("totalDiscount", 0.0)
                .hasFieldOrPropertyWithValue("totalFinalPrice", 880.0);
    }

    @Test
    public void testUpdateCart_withUserThatDoesNotExist() {
        assertThatThrownBy(() -> {
            cartService.updateCart(123, coffeeMachine_id, 0);
        }).isInstanceOf(CustomException.class)
                .hasMessageContaining("User 123 does not exist");
    }

    @Test
    public void testUpdateCart_withProductThatDoesNotExist() {
        assertThatThrownBy(() -> {
            cartService.updateCart(alice_id, 123, 0);
        }).isInstanceOf(CustomException.class)
                .hasMessageContaining("Product 123 does not exist");
    }

    @Test
    public void testUpdateCart_removeProductThatIsNotInCart() {
        Cart cart = cartService.updateCart(alice_id, coffeeMachine_id, 0);
        Assertions
                .assertThat(cart)
                .isNotNull()
                .hasFieldOrPropertyWithValue("orderItems", new HashMap<>());
    }

    @Test
    public void testUpdateCart_withNegativeQuantity() {
        Cart cart = cartService.updateCart(alice_id, coffeeMachine_id, 4);
        assertThat(cart.getOrderItems())
                .isNotEmpty()
                .hasSize(1);

        // update with negative quantity -> will remove all of this product from basket
        cart = cartService.updateCart(alice_id, coffeeMachine_id, -12);
        assertThat(cart.getOrderItems())
                .isEmpty();
    }
}



