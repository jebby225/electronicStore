package com.bullish.electronicStore.controller;

import com.bullish.electronicStore.common.ApiResponse;
import com.bullish.electronicStore.entity.Product;
import com.bullish.electronicStore.repository.ProductRepository;
import com.bullish.electronicStore.service.ProductService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/api/products", produces="application/json")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/list")
    public List<Product> getProducts() {
        List<Product> body = productService.listProducts();
        String json = new Gson().toJson(body);

        System.out.println("body" + json);
        return body;
    }

    @PostMapping("/add")
    public Product addProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }

    @DeleteMapping("/delete/{code}")
    public void deleteProduct(@PathVariable String code){
        productService.deleteProductByCode(code);
    }



}
