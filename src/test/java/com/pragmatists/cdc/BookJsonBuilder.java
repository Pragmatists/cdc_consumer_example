package com.pragmatists.cdc;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class BookJsonBuilder {

    private ObjectNode json = new ObjectMapper().createObjectNode();

    public static BookJsonBuilder bookJson() {
        return new BookJsonBuilder();
    }

    public BookJsonBuilder id(String id) {
        json.put("id", id);
        return this;
    }

    public BookJsonBuilder author(String author) {
        json.put("author", author);
        return this;
    }

    public BookJsonBuilder title(String title) {
        json.put("title", title);
        return this;
    }

    public BookJsonBuilder year(Integer year) {
        json.put("year", year);
        return this;
    }

    public JsonNode toJsonNode() {
        return json;
    }
}
