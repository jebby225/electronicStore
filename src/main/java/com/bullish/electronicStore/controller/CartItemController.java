package com.bullish.electronicStore.controller;

import com.bullish.electronicStore.entity.CartItem;
import com.bullish.electronicStore.entity.Product;
import com.bullish.electronicStore.entity.User;
import com.bullish.electronicStore.service.CartItemService;
import com.bullish.electronicStore.service.ProductService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/api/cartItems", produces="application/json")
public class CartItemController {

  //  @Autowired
  //  CartItemService cartItemService;

  /*  @GetMapping("/list")
    public List<CartItem> listCartItems(@RequestBody User user) {
        return cartItemService.listCartItem(user);
    }

    @PostMapping("/add")
    public CartItem addCartItem(@RequestBody CartItem cartItem) {
        return cartItemService.addCartItem(cartItem);
    } */
}
