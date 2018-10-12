package com.pragmatists.cdc;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.github.tomakehurst.wiremock.verification.LoggedRequest;
import com.pragmatists.cdc.books.BooksProvider;
import com.pragmatists.cdc.infrastructure.BooksProviderConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.SocketUtils;

import java.io.IOException;

public class BaseConsumerTest {
    public static final int PROVIDER_PORT = SocketUtils.findAvailableTcpPort();
    @Rule
    public WireMockRule booksProviderWiremock;
    @Autowired
    private BooksProviderConfiguration providerConfiguration;
    protected BooksProvider booksProvider;

    @Before
    public void setUp() {
        providerConfiguration.setPort(PROVIDER_PORT);
        booksProviderWiremock = new WireMockRule(PROVIDER_PORT);
        booksProviderWiremock.start();
        booksProvider = new BooksProvider(booksProviderWiremock);
    }

    @After
    public void tearDown() throws Exception {
        booksProviderWiremock.stop();
    }

    protected JsonNode jsonFrom(LoggedRequest request) throws IOException {
        String bodyAsString = request.getBodyAsString();
        return new ObjectMapper().readTree(bodyAsString);
    }
}
