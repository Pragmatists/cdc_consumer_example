package com.pragmatists.cdc;

import com.github.tomakehurst.wiremock.junit.WireMockRule;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class GettingBooksBuilder {
    private WireMockRule wiremock;

    public GettingBooksBuilder(WireMockRule wiremock) {
        this.wiremock = wiremock;
    }

    public void succeedsWithJson(String json) {
        wiremock.stubFor(get(urlEqualTo("/books"))
                .willReturn(aResponse()
                        .withBody(json)
                        .withStatus(200)));
    }
}
