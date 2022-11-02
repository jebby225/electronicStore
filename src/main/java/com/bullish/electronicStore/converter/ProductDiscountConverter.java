package com.bullish.electronicStore.converter;

import com.bullish.electronicStore.model.ProductDiscount;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.AttributeConverter;

public class ProductDiscountConverter implements AttributeConverter<ProductDiscount, String> {

    @Autowired
    ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    public String convertToDatabaseColumn(ProductDiscount attribute) {
        return objectMapper.writeValueAsString(attribute);
    }

    @SneakyThrows
    @Override
    public ProductDiscount convertToEntityAttribute(String dbData) {
        return objectMapper.readValue(dbData, ProductDiscount.class);
    }
}
