package com.pragmatists.cdc.domain;

import com.fasterxml.jackson.databind.type.TypeFactory;
import com.pragmatists.cdc.domain.Book;
import com.pragmatists.cdc.ui.JsonMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class BooksReader {

    private final OkHttpClient httpClient;
    private final JsonMapper jsonMapper;

    @Autowired
    public BooksReader(JsonMapper jsonMapper) {
        this.jsonMapper = jsonMapper;
        httpClient = new OkHttpClient.Builder().build();
    }

    public List<Book> all() {
        Request request = new Request.Builder().url("http://localhost:8080/books").build();

        try {
            Response response = httpClient.newCall(request).execute();
            return jsonMapper.transform(response, TypeFactory.defaultInstance().constructCollectionType(List.class, Book.class));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
