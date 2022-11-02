package com.bullish.electronicStore.service;

import com.bullish.electronicStore.entity.Product;
import com.bullish.electronicStore.exception.CustomException;
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

    @Transactional
    public void deleteProductByCode(String productCode) {
        if (productRepository.findByCode(productCode) == null) {
            throw new CustomException("Product does not exist");
        }
        productRepository.deleteByCode(productCode);
    }

}
