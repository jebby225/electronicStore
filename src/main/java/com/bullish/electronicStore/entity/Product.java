package com.bullish.electronicStore.entity;

import jdk.jfr.DataAmount;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;

@Entity
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
    private double price;
    @NotNull
    private String description;

    public String getCode(){
        return code;
    }

    public String getName(){
        return name;
    }

    public String getImageURL(){
        return imageURL;
    }

    public double getPrice(){
        return price;
    }

    public String getDescription(){
        return description;
    }

    Product() {}

    public Product(String code, String name, String imageURL, double price, String description) {
        super();
        this.code = code;
        this.name = name;
        this.imageURL = imageURL;
        this.price = price;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                '}';
    }

}
