package com.pragmatists.cdc;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.verification.LoggedRequest;
import com.pragmatists.cdc.domain.Book;
import com.pragmatists.cdc.domain.Books;
import com.pragmatists.cdc.domain.BooksService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

import static com.pragmatists.cdc.BookJsonBuilder.bookJson;
import static com.pragmatists.cdc.BooksJsonBuilder.booksJson;
import static com.pragmatists.cdc.ProjectAssertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConsumerApplicationTests extends BaseConsumerTests {

    @Autowired
    BooksService booksService;

    @Test
    public void loadsOneBooks() {
        String bookJson = booksJson()
                .add(bookJson()
                        .author("Tolkien")
                        .title("Drużyna Pierścienia")
                        .year(1954)
                        .id("123567")
                        .toJsonNode()
                ).build();
        booksProvider.givenGettingBooks().succeedsWithJson(bookJson);

        Books books = booksService.all();

        assertThat(books.books).hasSize(1);
        assertThat(books.books.get(0))
                .hasAuthor("Tolkien")
                .hasTitle("Drużyna Pierścienia")
                .hasYear(1954)
                .hasId("123567");
    }

    @Test
    public void createsBook() {
        booksProvider.givenAddingNewBook().succeeds();

        booksService.add(new Book(null, "Drużyna Pierścienia", "Tolkien", 1954));

        List<LoggedRequest> requests = getPostRequestsForBooks();
        assertThat(requests).hasSize(1);
    }

    @Test
    public void createsBookWithGivenValues() throws IOException {
        booksProvider.givenAddingNewBook().succeeds();
        Book book = new Book(null, "Drużyna Pierścienia", "Tolkien", 1954);

        booksService.add(book);

        List<LoggedRequest> requests = getPostRequestsForBooks();
        JsonNode json = asJsonNode(requests.get(0));
        assertThat(json.get("title").textValue()).isEqualTo("Drużyna Pierścienia");
        assertThat(json.get("author").textValue()).isEqualTo("Tolkien");
        assertThat(json.get("year").numberValue()).isEqualTo(1954);
    }

    private JsonNode asJsonNode(LoggedRequest request) throws IOException {
        String body = request.getBodyAsString();
        return new ObjectMapper().readTree(body);
    }

    private Book someBook() {
        return new Book(null, "Drużyna Pierścienia", "Tolkien", 1954);
    }

    private List<LoggedRequest> getPostRequestsForBooks() {
        return wiremock
                .findAll(WireMock.postRequestedFor(WireMock.urlEqualTo("/books")));
    }
}
