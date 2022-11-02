package com.bullish.electronicStore.service;

import com.bullish.electronicStore.entity.Cart;
import com.bullish.electronicStore.entity.CartItem;
import com.bullish.electronicStore.entity.Product;
import com.bullish.electronicStore.entity.User;
import com.bullish.electronicStore.enums.UserRoles;
import com.bullish.electronicStore.repository.CartItemRepository;
import com.bullish.electronicStore.repository.CartRepository;
import com.bullish.electronicStore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    public Cart listCarts(User user) {
        return cartRepository.findByUser(user);
    }

    public Cart addToCart(User user, CartItem newCartItem) {

        Cart cart = cartRepository.findByUser(user);

        // if no cart - first time adding to cart, then create cart
        if(cart == null) {
            cart = new Cart(user);
            newCartItem.setCart(cart);
            user.setCart(cart);

            Set<CartItem> cartItemSet = new HashSet<>();
            cartItemSet.add(newCartItem);

            cart.setCartItems(cartItemSet);

            cartRepository.save(cart);

        } else {
            List<CartItem> findItems = cart.getCartItems()
                    .stream()
                    .filter(p -> p.getProduct().getCode() == newCartItem.getProduct().getCode())
                    .collect(Collectors.toList());

            if(findItems.isEmpty()) {
                cart.getCartItems().add(newCartItem);
            } else {
                findItems.get(0).setQuantity(newCartItem.getQuantity());
            }
        }

        return cart;
    }

}
