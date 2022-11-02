package com.bullish.electronicStore.model;

import com.bullish.electronicStore.converter.CartConverter;
import com.bullish.electronicStore.enums.UserRoles;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Getter
@Setter
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

    @Convert(converter = CartConverter.class)
    @Lob
    private Cart cart;


    public User() {}

    public User(String userName, String email, UserRoles role) {
        super();
        this.userName = userName;
        this.email = email;
        this.role = role;
        this.cart = new Cart();
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
