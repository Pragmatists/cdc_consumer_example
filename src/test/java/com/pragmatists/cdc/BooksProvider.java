package com.pragmatists.cdc;

import com.github.tomakehurst.wiremock.junit.WireMockRule;

public class BooksProvider {
    private WireMockRule wiremock;

    public BooksProvider(WireMockRule wiremock) {
        this.wiremock = wiremock;
    }

    protected GettingBooksBuilder givenGettingBooks() {
        return new GettingBooksBuilder(wiremock);
    }

    public AddingBooksBuilder givenAddingNewBook() {
        return new AddingBooksBuilder(wiremock);
    }
}
