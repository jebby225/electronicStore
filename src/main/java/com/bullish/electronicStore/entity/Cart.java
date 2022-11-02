package com.bullish.electronicStore.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public void setCartItems(Set<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public Set<CartItem> getCartItems() {
        return cartItems;
    }

    @OneToMany(mappedBy = "cart", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<CartItem> cartItems = new HashSet<>();

    @OneToOne //(mappedBy = "cart")
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    public User getUser() {
        return user;
   }

    public void setUser(User user) {
        this.user = user;
    }

    public Cart() {}

    public Cart(User user) {
        this.user = user;
        //this.cartItemList = new ArrayList<>();
    }

}
