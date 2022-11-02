package com.bullish.electronicStore.controller;


import com.bullish.electronicStore.model.Cart;
import com.bullish.electronicStore.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api/carts", produces="application/json")
public class CartController {

    @Autowired
    CartService cartService;

    @GetMapping
    public Cart getCart(@RequestParam(value = "userId") int userId) {
        return cartService.listCarts(userId);
    }

    @PostMapping
    public Cart updateCart(@RequestParam(value = "userId") int userId,
                             @RequestParam(value = "productId") int productId,
                             @RequestParam(value = "quantity") int quantity) {
        return cartService.updateCart(userId, productId, quantity);
    }
}
