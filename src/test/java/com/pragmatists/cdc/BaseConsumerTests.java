package com.pragmatists.cdc;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.pragmatists.cdc.infrastructure.BooksProviderConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.SocketUtils;

public class BaseConsumerTests {
    public static final int PROVIDER_PORT = SocketUtils.findAvailableTcpPort();
    @Rule
    public WireMockRule wiremock;
    @Autowired
    BooksProviderConfiguration providerConfiguration;

    protected BooksProvider booksProvider;

    @Before
    public void setUp() {
        providerConfiguration.setPort(PROVIDER_PORT);
        wiremock = new WireMockRule(PROVIDER_PORT);
        wiremock.start();
        booksProvider = new BooksProvider(wiremock);
    }

    @After
    public void tearDown() throws Exception {
        wiremock.stop();
    }

}
