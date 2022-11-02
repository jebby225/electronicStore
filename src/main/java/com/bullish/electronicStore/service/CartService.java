package com.bullish.electronicStore.service;

import com.bullish.electronicStore.entity.*;
import com.bullish.electronicStore.enums.UserRoles;
import com.bullish.electronicStore.exception.CustomException;
import com.bullish.electronicStore.repository.CartItemRepository;
import com.bullish.electronicStore.repository.CartRepository;
import com.bullish.electronicStore.repository.ProductRepository;
import com.bullish.electronicStore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    public Cart listCarts(int userId) {
        User user = userRepository.findById(userId);
        if(user == null)
            throw new CustomException("User does not exist");

        return cartRepository.findByUser(user);
    }

    public Cart addToCart(int userId, int productId, int quantity) {
        User user = userRepository.findById(userId);
        Product product = productRepository.findById(productId);

        if(product == null)
            throw new CustomException("Product does not exist");
        if(user == null)
            throw new CustomException("User does not exist");

        CartItem newCartItem = new CartItem(product, quantity);

        Cart cart = cartRepository.findByUser(user);

        // if no cart - first time adding to cart, then create cart
        if(cart == null) {
            cart = new Cart(user);
            newCartItem.setCart(cart);
            user.setCart(cart);
            cart.setUser(user);

            CartItem saveCartItem = cartItemRepository.save(newCartItem);

            Set<CartItem> cartItemSet = new HashSet<>();
            cartItemSet.add(saveCartItem);

            cart.setCartItems(cartItemSet);

            cartRepository.save(cart);

        } else {
            CartItem findCartItem = cartItemRepository.findByCartAndProduct(cart, newCartItem.getProduct());

            if(findCartItem != null) {
                findCartItem.setQuantity(newCartItem.getQuantity());
                cartItemRepository.save(findCartItem);
            } else {
                newCartItem.setCart(cart);
                cart.addCartItem(newCartItem);

                cartRepository.save(cart);
            }
        }
        return cart;
    }

    public Cart updateCart(int userId, int cartItemId, int newQuantity) {
        User user = userRepository.findById(userId);
        CartItem cartItem = cartItemRepository.findById(cartItemId);

        if(cartItem == null)
            throw new CustomException("cart item does not exist");
        if(user == null)
            throw new CustomException("user does not exist");

        cartItem.setQuantity(newQuantity);
        cartItemRepository.save(cartItem);

        if(newQuantity <= 0) {
            Cart cart = cartItem.getCart();
            cart.removeCartItem(cartItem);
            cartRepository.save(cart);
        }

        return cartItem.getCart();
    }

    public CheckoutReceipt checkoutCart(int userId) {
        User user = userRepository.findById(userId);
        Cart cart = cartRepository.findByUser(user);

        if(cart == null)
            throw new CustomException("cart does not exist");
        if(user == null)
            throw new CustomException("user does not exist");


        List<CheckoutItem> checkoutItems = new ArrayList<>();

        for (CartItem cartItem : cart.getCartItems()) {
            checkoutItems.add(new CheckoutItem(
                    cartItem.getProduct().getCode(),
                    cartItem.getProduct().getName(),
                    cartItem.getQuantity(),
                    cartItem.getProduct().getPrice(),
                    ""));
        }

        CheckoutReceipt checkoutReceipt = new CheckoutReceipt(checkoutItems);
        checkoutReceipt.setTotalDiscount(0.0);
        checkoutReceipt.setTotalFinalPrice(0.0);
        checkoutReceipt.setTotalOriginalPrice(0.0);

        return checkoutReceipt;
    }

}
