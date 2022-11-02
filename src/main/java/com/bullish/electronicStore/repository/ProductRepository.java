package com.bullish.electronicStore.repository;

import com.bullish.electronicStore.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    void deleteByCode(String code);
    Product findByCode(String code);

    Product findById(int id);
}
