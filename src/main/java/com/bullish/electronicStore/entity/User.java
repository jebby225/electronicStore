package com.bullish.electronicStore.entity;

import com.bullish.electronicStore.enums.UserRoles;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String userName;

    @Column(unique=true)
    @NotNull
    private String email;

    @Enumerated(EnumType.STRING)
    private UserRoles role;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id")
    private Cart cart;


    //@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    //@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    //@NotFound(action = NotFoundAction.IGNORE)
   // @OneToMany(mappedBy = "user")
   // private Set<CartItem> cartItems = new HashSet<>();

    public String getUserName() {
        return userName;
    }
    public String getEmail() {
        return email;
    }

    public UserRoles getRole() { return role; }

    public void setCart(Cart cart) { this.cart = cart;}

  /*  public Set<CartItem>  getCartItems() {
        return cartItems;
    }

    public void setCartItems(Set<CartItem> cartItems) {
        this.cartItems = cartItems;
    } */

    public User() {}

    public User(String userName, String email, UserRoles role) {
        super();
        this.userName = userName;
        this.email = email;
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

}
