package com.pragmatists.cdc.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.pragmatists.cdc.infrastructure.BooksProviderConfiguration;
import com.pragmatists.cdc.ui.JsonMapper;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static java.lang.String.format;

@Service
public class BooksService {

    private final OkHttpClient httpClient;
    private final JsonMapper jsonMapper;
    private final BooksProviderConfiguration providerConfiguration;

    @Autowired
    public BooksService(JsonMapper jsonMapper, BooksProviderConfiguration providerConfiguration) {
        this.jsonMapper = jsonMapper;
        this.providerConfiguration = providerConfiguration;
        httpClient = new OkHttpClient.Builder().build();
    }

    public Books all() {
        Request request = new Request.Builder().url(format("http://%s:%d/books", providerConfiguration.getHost(), providerConfiguration.getPort())).build();

        try {
            Response response = httpClient.newCall(request).execute();
            return jsonMapper.transform(response, TypeFactory.defaultInstance().constructType(Books.class));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void add(Book book) {
        try {
            String bookJson = new ObjectMapper().writeValueAsString(book);

            Request request = new Request.Builder()
                    .post(RequestBody.create(MediaType.parse("application/json"), bookJson))
                    .url(format("http://%s:%d/books", providerConfiguration.getHost(), providerConfiguration.getPort()))
                    .build();

            httpClient.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }




}
