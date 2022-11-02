package com.bullish.electronicStore.service;

import com.bullish.electronicStore.model.*;
import com.bullish.electronicStore.exception.CustomException;
import com.bullish.electronicStore.repository.ProductRepository;
import com.bullish.electronicStore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CartService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductDiscountService productDiscountService;

    public Cart listCarts(int userId) {
        User user = userRepository.findById(userId);
        if(user == null)
            throw new CustomException("User does not exist");

        return user.getCart(); // cartRepository.findByUser(user);
    }

    public Cart updateCart(int userId, int productId, int quantity) {
        User user = userRepository.findById(userId);
        Product product = productRepository.findById(productId);

        if (product == null)
            throw new CustomException("Product does not exist");
        if (user == null)
            throw new CustomException("User does not exist");

        if(quantity <= 0) {
            user.getCart().getOrderItems().remove(productId);
        } else {
            OrderItem orderItem = new OrderItem(product.getCode(), product.getName(), quantity, product.getPrice());
            Double discount = 0.0;

            if(product.getProductDiscount() != null) {
                orderItem.setAppliedDiscount(product.getProductDiscount().getDescription());
                discount = productDiscountService.calculateTotalProductDiscount(product.getProductDiscount(), orderItem);
                orderItem.setDiscount(discount);
            }

            if(user.getCart().getOrderItems().containsKey(productId))
                user.getCart().getOrderItems().replace(productId, orderItem);
            else
                user.getCart().getOrderItems().put(productId, orderItem);

            orderItem.setSubTotal(product.getPrice() * quantity - discount);
        }

        //*** Update price ****//
        Double totalDiscount = 0.0;
        Double totalOriginalPrice = 0.0;

        for(OrderItem orderItem : user.getCart().getOrderItems().values()) {
            totalDiscount +=orderItem.getDiscount();
            totalOriginalPrice += orderItem.getPrice() * orderItem.getQuantity();
        }

        user.getCart().setTotalDiscount(totalDiscount);
        user.getCart().setTotalOriginalPrice(totalOriginalPrice);
        user.getCart().setTotalFinalPrice(totalOriginalPrice - totalDiscount);

        userRepository.save(user);
        return user.getCart();
    }

}
