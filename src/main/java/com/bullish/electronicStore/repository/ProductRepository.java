package com.bullish.electronicStore.repository;

import com.bullish.electronicStore.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    void deleteByCode(String code);
    Product findByCode(String code);
}
