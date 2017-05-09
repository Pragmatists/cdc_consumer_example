package com.pragmatists.cdc.ui;


import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.Response;

import java.io.IOException;

public class JsonMapper {
    private ObjectMapper objectMapper;

    public JsonMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public <T> T transform(Response response, JavaType resultClass) {
        transformOnlySuccessfulResponse(response);
        try {
            return objectMapper.readValue(responseBodyAsString(response), resultClass);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void transformOnlySuccessfulResponse(Response response) {
        if (!response.isSuccessful()) {
            throw new IllegalArgumentException(String.format("Couldn't transform response%n%s", responseBodyAsString(response)));
        }
    }

    private String responseBodyAsString(Response response) {
        try {
            return response.body().string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}