package com.pragmatists.cdc.books;

import com.github.tomakehurst.wiremock.junit.WireMockRule;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class GettingBooksBuilder {
    private WireMockRule wiremock;

    public GettingBooksBuilder(WireMockRule wiremock) {
        this.wiremock = wiremock;
    }

    public void succeedsWithResponse(String responseBody) {
        wiremock.stubFor(get(urlEqualTo("/books"))
                .willReturn(aResponse()
                        .withBody(responseBody)
                        .withStatus(200)));
    }
}
