package com.bullish.electronicStore.model;

import com.bullish.electronicStore.converter.ProductDiscountConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique=true)
    @NotNull
    private String code;
    @NotNull
    private String name;
    @NotNull
    private String imageURL;
    @NotNull
    private Double price;
    @NotNull
    private String description;

    @Convert(converter = ProductDiscountConverter.class)
    private ProductDiscount productDiscount;

    public Product(String code, String name, String imageURL, Double price, String description) {
        super();
        this.code = code;
        this.name = name;
        this.imageURL = imageURL;
        this.price = price;
        this.description = description;
    }
}
