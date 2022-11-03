package com.bullish.electronicStore.controller;

import com.bullish.electronicStore.model.Product;
import com.bullish.electronicStore.model.ProductDiscount;
import com.bullish.electronicStore.service.ProductService;
import com.bullish.electronicStore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping(path="/api/products", produces="application/json")
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;

    @GetMapping
    public List<Product> getProducts() {
        return productService.listProducts();
    }

    @PostMapping
    public Product addProduct(@RequestBody Product product,
                              @RequestHeader("userId") int userId) {
        if(!userService.isAdminUser(userId))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, String.format("User %s is not an admin", userId));
        return productService.addProduct(product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable int id,
                              @RequestHeader("userId") int userId){
        if(!userService.isAdminUser(userId))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, String.format("User %s is not an admin", userId));
        productService.deleteProductById(id);
    }

    @PostMapping("/discount/{id}")
    public void applyDiscount(@PathVariable int id,
                              @RequestBody ProductDiscount productDiscount,
                              @RequestHeader("userId") int userId) {
        if(!userService.isAdminUser(userId))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, String.format("User %s is not an admin", userId));
        productService.addProductDiscount(id, productDiscount);
    }

}
