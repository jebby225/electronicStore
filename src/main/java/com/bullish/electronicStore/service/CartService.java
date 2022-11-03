package com.bullish.electronicStore.service;

import com.bullish.electronicStore.model.*;
import com.bullish.electronicStore.exception.CustomException;
import com.bullish.electronicStore.repository.ProductRepository;
import com.bullish.electronicStore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

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
            throw new CustomException(String.format("User %s does not exist", userId));

        // Need to refresh price / discount for case when admin applied discount code
        // after customers have add product to cart
        Cart cart = user.getCart();


        Double totalDiscount = 0.0;
        Double totalOriginalPrice = 0.0;

        for(Map.Entry<Integer, OrderItem> kvp : cart.getOrderItems().entrySet()) {
            Integer productId = kvp.getKey();
            OrderItem orderItem = kvp.getValue();
            Product product = productRepository.findById(productId).get();


            Double discount = 0.0;
            if (product.getProductDiscount() != null) {
                orderItem.setAppliedDiscount(product.getProductDiscount().getDescription());
                discount = productDiscountService.calculateTotalProductDiscount(product.getProductDiscount(), orderItem);
                orderItem.setDiscount(discount);
                totalDiscount += discount;
            }
            totalOriginalPrice += orderItem.getPrice() * orderItem.getQuantity();
        }

        //*** Update total price and discount ****/
        cart.setTotalDiscount(totalDiscount);
        cart.setTotalOriginalPrice(totalOriginalPrice);
        cart.setTotalFinalPrice(totalOriginalPrice - totalDiscount);

        return cart;
    }

    public Cart updateCart(int userId, int productId, int quantity) {
        User user = userRepository.findById(userId);
        Product product = productRepository.findById(productId);

        if (product == null)
            throw new CustomException(String.format("Product %s does not exist", productId));
        if (user == null)
            throw new CustomException(String.format("User %s does not exist", userId));

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

        //*** Update total price and discount ****//
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