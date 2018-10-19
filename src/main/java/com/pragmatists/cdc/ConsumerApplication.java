package com.pragmatists.cdc;

import com.pragmatists.cdc.domain.Books;
import com.pragmatists.cdc.domain.BooksService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(BooksReaderConfiguration.class)
public class ConsumerApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(ConsumerApplication.class, args);

        BooksService booksService = applicationContext.getBean(BooksService.class);
        Books books = booksService.all();

        System.out.println(String.format("Super we have read %d books: %s", books.books.size(), books.books));
    }
}
