package com.bullish.electronicStore.service;

import com.bullish.electronicStore.entity.CartItem;
import com.bullish.electronicStore.entity.Product;
import com.bullish.electronicStore.entity.User;
import com.bullish.electronicStore.repository.CartItemRepository;
import com.bullish.electronicStore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemService {
    @Autowired
    private CartItemRepository cartItemRepository;



/*
    public List<CartItem> listCartItem(User user) {

        return cartItemRepository.findAll();
       // return cartItemRepository.findByUser(user);
    }

    public CartItem addCartItem(CartItem cartItem) {
        CartItem cartItemRes = cartItemRepository.findByUserAndProduct(cartItem.getUser(), cartItem.getProduct());
        if(cartItemRes == null)
            return cartItemRepository.save(cartItem);
        else {
            cartItemRes.setQuantity(cartItemRes.getQuantity() + cartItem.getQuantity());
            return cartItemRes;
        }
    }
*/



}
