package com.pragmatists.cdc.books;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class BooksJsonBuilder {

    private final ArrayNode array = new ObjectMapper().createArrayNode();
    private ObjectNode json = new ObjectMapper().createObjectNode();

    public BooksJsonBuilder() {
        json.set("books", array);
    }

    public String build() {
        return json.toString();
    }

    public static BooksJsonBuilder booksJson() {
        return new BooksJsonBuilder();
    }

    public BooksJsonBuilder add(JsonNode item) {
        array.add(item);
        return this;
    }

    public static class BookJsonBuilder {

        private ObjectNode json = new ObjectMapper().createObjectNode();

        public String build() {
            return json.toString();
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

        public BookJsonBuilder year(int year) {
            json.put("year", year);
            return this;
        }

        public JsonNode asJsonNode() {
            return json;
        }
    }

    public static BookJsonBuilder bookJson() {
        return new BookJsonBuilder();
    }
}
