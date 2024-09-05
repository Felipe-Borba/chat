package com.felipeborba;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.nio.charset.StandardCharsets;

public class Serializer {
    private final ObjectMapper objectMapper;

    Serializer() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    Message read(byte[] message) {
        try {
            return objectMapper.readValue(message, Message.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    byte[] serialize(Message message) throws JsonProcessingException {
        return objectMapper.writeValueAsString(message).getBytes(StandardCharsets.UTF_8);
    }
}
