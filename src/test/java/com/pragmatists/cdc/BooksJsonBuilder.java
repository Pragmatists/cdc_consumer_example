package com.pragmatists.cdc;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class BooksJsonBuilder {
    private ObjectNode json = new ObjectMapper().createObjectNode();
    private ArrayNode array = new ObjectMapper().createArrayNode();

    public BooksJsonBuilder() {
        json.set("books", array);
    }

    public BooksJsonBuilder add(JsonNode item) {
        array.add(item);
        return this;
    }

    public String build() {
        return json.toString();
    }

    public static BooksJsonBuilder booksJson() {
        return new BooksJsonBuilder();
    }
}
