package com.bullish.electronicStore.repository;

import com.bullish.electronicStore.entity.Cart;
import com.bullish.electronicStore.entity.CartItem;
import com.bullish.electronicStore.entity.Product;
import com.bullish.electronicStore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

    List<CartItem> findAll();
   // List<CartItem> findByUser(User user);
    CartItem findByCartAndProduct(Cart cart, Product product);

}

