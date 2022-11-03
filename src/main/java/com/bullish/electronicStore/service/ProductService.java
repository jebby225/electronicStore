package com.bullish.electronicStore.service;

import com.bullish.electronicStore.exception.CustomException;
import com.bullish.electronicStore.model.Product;
import com.bullish.electronicStore.model.ProductDiscount;
import com.bullish.electronicStore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> listProducts() {
        return productRepository.findAll();
    }

    public Product findByProductCode(String code) { return productRepository.findByCode(code);}

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public Product addProductDiscount(int productId, ProductDiscount productDiscount) {
        Product p = productRepository.findById(productId);
        if(!productRepository.existsById(productId))
            throw new CustomException(String.format("Product %s does not exist", productId));
        p.setProductDiscount(productDiscount);
        return productRepository.save(p);
    }

    public void deleteProductById(int productId) {
        if(!productRepository.existsById(productId))
            throw new CustomException(String.format("Product %s does not exist", productId));
        productRepository.deleteById(productId);
    }

}
