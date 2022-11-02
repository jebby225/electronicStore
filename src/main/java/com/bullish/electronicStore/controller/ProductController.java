package com.bullish.electronicStore.controller;

import com.bullish.electronicStore.model.Product;
import com.bullish.electronicStore.model.ProductDiscount;
import com.bullish.electronicStore.service.ProductService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/api/products", produces="application/json")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping
    public List<Product> getProducts() {
        List<Product> body = productService.listProducts();
        String json = new Gson().toJson(body);

        System.out.println("body" + json);
        return body;
    }

    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }

    @DeleteMapping("/{code}")
    public void deleteProduct(@PathVariable String code){
        productService.deleteProductByCode(code);
    }

    @PatchMapping("/{id}")
    public void applyDiscount(@PathVariable int id, @RequestBody ProductDiscount productDiscount) {
        productService.addProductDiscount(id, productDiscount);
    }

}
