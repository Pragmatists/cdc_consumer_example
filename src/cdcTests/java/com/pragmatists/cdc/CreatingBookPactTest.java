package com.pragmatists.cdc;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRule;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.PactFragment;
import com.pragmatists.cdc.domain.Book;
import com.pragmatists.cdc.domain.BooksReader;
import com.pragmatists.cdc.infrastructure.BooksProviderConfiguration;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BooksReaderConfiguration.class, webEnvironment = RANDOM_PORT)
public class CreatingBookPactTest {
    @Rule
    public PactProviderRule mockProvider = new PactProviderRule("books_provider", this);

    @Autowired
    private BooksProviderConfiguration providerConfiguration;

    @Autowired
    private BooksReader booksReader;

    @Before
    public void setUpProviderLocation() throws Exception {
        providerConfiguration
                .setHost(mockProvider.getConfig().getHostname())
                .setPort(mockProvider.getConfig().getPort());
    }

    @Pact(provider = "books_provider", consumer = "books_consumer")
    public PactFragment fragmentForCreatingBook(PactDslWithProvider builder) {
        return builder
                .uponReceiving("Request to create a book")
                .method("POST")
                .path("/books")
                .headers("Content-Type", "application/json; charset=utf-8")
                .body(createBookBody())
                .willRespondWith()
                .status(204)
                .toFragment();
    }

    private DslPart createBookBody() {
        return new PactDslJsonBody()
                .stringType("author")
                .stringType("title")
                .numberType("year")
                .nullValue("id");
    }

    @PactVerification("books_provider")
    @Test
    public void verifyPactForCreatingBook() {
        assertThatCode(() -> {
            booksReader.add(new Book(null, "title", "author", 1900));
        }).doesNotThrowAnyException();
    }
}
