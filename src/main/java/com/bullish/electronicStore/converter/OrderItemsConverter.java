package com.bullish.electronicStore.converter;

import com.bullish.electronicStore.model.OrderItem;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.AttributeConverter;
import java.util.Map;

public class OrderItemsConverter implements AttributeConverter<Map<Integer, OrderItem>, String> {

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public String convertToDatabaseColumn(Map<Integer, OrderItem> attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<Integer, OrderItem> convertToEntityAttribute(String dbData) {
        try {
            TypeReference<Map<Integer, OrderItem>> typeRef
                    = new TypeReference<Map<Integer, OrderItem>>() {};

            return objectMapper.readValue(dbData, typeRef);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
