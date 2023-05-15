package dev.gilbert.zacharia.managercraft.models;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.SneakyThrows;

public class DataToObject {

    private static final ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    @SneakyThrows
    public String toJson() {
        return mapper.writeValueAsString(this);
    }
}
