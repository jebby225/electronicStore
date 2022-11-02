package com.bullish.electronicStore.controller;


import com.bullish.electronicStore.entity.Cart;
import com.bullish.electronicStore.entity.CartItem;
import com.bullish.electronicStore.entity.Product;
import com.bullish.electronicStore.entity.User;
import com.bullish.electronicStore.service.CartService;
import com.bullish.electronicStore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/api/carts", produces="application/json")
public class CartCotroller {

    @Autowired
    CartService cartService;

    @GetMapping("/list")
    public Cart getCart(@RequestParam(value = "userId") int userId) {
        return cartService.listCarts(userId);
    }

    @PostMapping("/addItem")
    public Cart addItemtoCart(@RequestParam(value = "userId") int userId,
                             @RequestParam(value = "productId") int productId,
                             @RequestParam(value = "quantity") int quantity) {
        return cartService.addToCart(userId, productId, quantity);
    }

    @PostMapping("/updateItem")
    public Cart updateCart(@RequestParam(value = "userId") int userId,
                         @RequestParam(value = "productId") int productId,
                         @RequestParam(value = "newQuantity") int quantity) {
        return cartService.updateCart(userId, productId, quantity);
    }
}
