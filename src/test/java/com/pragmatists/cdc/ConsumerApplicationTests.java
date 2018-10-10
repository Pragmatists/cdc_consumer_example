package com.pragmatists.cdc;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.pragmatists.cdc.domain.Books;
import com.pragmatists.cdc.domain.BooksReader;
import com.pragmatists.cdc.infrastructure.BooksProviderConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.SocketUtils;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConsumerApplicationTests {
    public static final int PROVIDER_PORT = SocketUtils.findAvailableTcpPort();

    @Autowired
    BooksReader booksReader;

    @Autowired
    BooksProviderConfiguration providerConfiguration;

    @Rule
    public WireMockRule wiremock;

    @Before
    public void setUp() {
        providerConfiguration.setPort(PROVIDER_PORT);
        wiremock = new WireMockRule(PROVIDER_PORT);
        wiremock.start();
    }

    @After
    public void tearDown() throws Exception {
        wiremock.stop();
    }

    @Test
    public void loadsEmptyBooks() {
        wiremock.stubFor(get(urlEqualTo("/books"))
                .willReturn(aResponse()
                        .withBody("{\"books\":[]}")
                        .withStatus(200)));

        Books books = booksReader.all();

        assertThat(books.books).isEmpty();
    }

}
