package com.bullish.electronicStore.converter;

import com.bullish.electronicStore.model.Cart;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.AttributeConverter;


public class CartConverter implements AttributeConverter<Cart, String> {

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public String convertToDatabaseColumn(Cart attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Cart convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, Cart.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
