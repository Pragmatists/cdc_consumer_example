package com.pragmatists.cdc;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRule;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.PactFragment;
import com.pragmatists.cdc.domain.Book;
import com.pragmatists.cdc.domain.Books;
import com.pragmatists.cdc.domain.BooksService;
import com.pragmatists.cdc.infrastructure.BooksProviderConfiguration;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BooksReaderConfiguration.class, webEnvironment = RANDOM_PORT)
public class ReadingAllBooksPactTest {

    @Rule
    public PactProviderRule mockProvider = new PactProviderRule("books_provider", this);

    @Autowired
    private BooksProviderConfiguration providerConfiguration;

    @Autowired
    private BooksService booksService;

    @Before
    public void setUpProviderLocation() throws Exception {
        providerConfiguration
                .setHost(mockProvider.getConfig().getHostname())
                .setPort(mockProvider.getConfig().getPort());
    }

    @Pact(provider = "books_provider", consumer = "books_consumer")
    public PactFragment createFragment(PactDslWithProvider builder) {
        return builder
                .given("there are books to read")
                .uponReceiving("Request to get all books")
                    .path("/books")
                    .method("GET")
                .willRespondWith()
                    .status(200)
                    .body(booksResponseBody())
                .toFragment();
    }

    @Test
    @PactVerification("books_provider")
    public void shouldReturnAllBooks() throws Exception {
        Books books = booksService.all();

        assertThat(books.books)
                .hasSize(2)
                .containsExactly(
                        new Book("0765377063", "The Three-Body Problem", null, 2007),
                        new Book("076537708X", "The Dark Forest", "Liu Cixin", 2008)
                );
    }

    private DslPart booksResponseBody() {
        return new PactDslJsonBody()
                .eachLike("books")
                        .stringType("id", "0765377063")
                        .stringType("title", "The Three-Body Problem")
                        .numberType("year", 2007)
                    .closeObject()
                    .object()
                        .stringType("id", "076537708X")
                        .stringType("title", "The Dark Forest")
                        .stringType("author", "Liu Cixin")
                        .numberType("year", 2008)
                    .closeObject()
                .closeArray();

    }
}
