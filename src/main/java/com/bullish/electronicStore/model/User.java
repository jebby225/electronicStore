package com.bullish.electronicStore.model;

import com.bullish.electronicStore.converter.CartConverter;
import com.bullish.electronicStore.enums.UserRoles;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
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
    private Cart cart = new Cart();

    public User(String userName, String email, UserRoles role) {
        super();
        this.userName = userName;
        this.email = email;
        this.role = role;
    }

}
