package com.pragmatists.cdc.books;

import com.github.tomakehurst.wiremock.junit.WireMockRule;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class AddingBooksBuilder {
    private WireMockRule wiremock;

    public AddingBooksBuilder(WireMockRule wiremock) {
        this.wiremock = wiremock;
    }

    public void succeeds() {
        wiremock.stubFor(post(urlEqualTo("/books"))
                .willReturn(aResponse().withStatus(204)));
    }
}
