package com.bullish.electronicStore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cascade;
import org.jetbrains.annotations.NotNull;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
//@Table(uniqueConstraints={
//        @UniqueConstraint(columnNames = {"product_id", "user_id"})
//})
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    private Cart cart;

    @NotNull
    private int quantity;

   // @ManyToOne
  //  @Cascade(CascadeType.ALL)
  //  @JoinColumn(name = "user_id")
    //@ManyToOne
    //@Cascade(CascadeType.SAVE_UPDATE)
    //@JoinColumn(name = "user_id")
    //private User user;

    @ManyToOne
    @JoinColumn(nullable = false, name = "product_id")
    private Product product;

    private LocalDate createdDate;

    //public void setUser(User user) {
    //    this.user = user;
    //}

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Product getProduct() {
        return this.product;
    }

    //public User getUser() {
    //    return this.user;
    //}

    public CartItem() {};

    public CartItem(User user, Product product, int quantity) {
        //this.user =user;
        this.product = product;
        this.quantity = quantity;
        //this.cart = cart;
        this.createdDate = LocalDate.now();
    }
}
