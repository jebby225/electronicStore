package com.bullish.electronicStore.controller;


import com.bullish.electronicStore.entity.Cart;
import com.bullish.electronicStore.entity.CartItem;
import com.bullish.electronicStore.entity.Product;
import com.bullish.electronicStore.entity.User;
import com.bullish.electronicStore.service.CartService;
import com.bullish.electronicStore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path="/api/carts", produces="application/json")
public class CartCotroller {

    @Autowired
    CartService cartService;

    @GetMapping("/list")
    public Cart getCarts(@RequestBody User user) {
        return cartService.listCarts(user);
    }

    @GetMapping("/addItem")
    public Cart getCarts(@RequestBody CartItem cartItem, @RequestBody User user) {
        return cartService.addToCart(user, cartItem);
    }





}
