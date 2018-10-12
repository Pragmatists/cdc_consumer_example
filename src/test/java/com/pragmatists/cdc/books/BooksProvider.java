package com.pragmatists.cdc.books;

import com.github.tomakehurst.wiremock.junit.WireMockRule;

public class BooksProvider {
    private WireMockRule wiremock;

    public BooksProvider(WireMockRule wiremock) {
        this.wiremock = wiremock;
    }

    public GettingBooksBuilder givenRequestForBooks() {
        return new GettingBooksBuilder(wiremock);
    }

    public AddingBooksBuilder givenRequestForAddingBook() {
        return new AddingBooksBuilder(wiremock);
    }
}
