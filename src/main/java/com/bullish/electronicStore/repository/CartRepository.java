package com.bullish.electronicStore.repository;

import com.bullish.electronicStore.entity.Cart;
import com.bullish.electronicStore.entity.Product;
import com.bullish.electronicStore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    List<Cart> findAll();
    Cart findByUser(User user);

}
