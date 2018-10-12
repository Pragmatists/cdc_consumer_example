package com.pragmatists.cdc.books;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.tomakehurst.wiremock.verification.LoggedRequest;
import com.pragmatists.cdc.BaseConsumerTest;
import com.pragmatists.cdc.domain.Book;
import com.pragmatists.cdc.domain.Books;
import com.pragmatists.cdc.domain.BooksReader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.anyRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.pragmatists.cdc.books.BooksJsonBuilder.bookJson;
import static com.pragmatists.cdc.books.BooksJsonBuilder.booksJson;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BooksTest extends BaseConsumerTest {

    @Autowired
    BooksReader booksReader;

    @Test
    public void loadsEmptyBooks() {
        booksProvider.givenRequestForBooks().succeedsWithResponse(booksJson().build());

        Books books = booksReader.all();

        assertThat(books.books).isEmpty();
    }

    @Test
    public void loadsOneBook() {
        booksProvider.givenRequestForBooks().succeedsWithResponse(booksJson()
                .add(bookJson()
                        .id("5")
                        .author("Tolkien")
                        .title("Dwie wieże")
                        .year(1968)
                        .asJsonNode()
                ).build());

        Books books = booksReader.all();

        assertThat(books.books).hasSize(1);
        assertThat(books.books.get(0).author).isEqualTo("Tolkien");
        assertThat(books.books.get(0).id).isEqualTo("5");
        assertThat(books.books.get(0).title).isEqualTo("Dwie wieże");
        assertThat(books.books.get(0).year).isEqualTo(1968);
    }

    @Test
    public void addsOneBook() throws IOException {
        booksProvider.givenRequestForAddingBook().succeeds();

        booksReader.add(new Book(null, "Drużyna pierścienia", "Tolkien", 1967));

        List<LoggedRequest> requests = booksProviderWiremock.findAll(anyRequestedFor(urlEqualTo("/books")));
        assertThat(requests).hasSize(1);
        JsonNode jsonNode = jsonFrom(requests.get(0));
        assertThat(jsonNode.get("title").textValue()).isEqualTo("Drużyna pierścienia");
        assertThat(jsonNode.get("author").textValue()).isEqualTo("Tolkien");
        assertThat(jsonNode.get("year").numberValue()).isEqualTo(1967);
    }

}
