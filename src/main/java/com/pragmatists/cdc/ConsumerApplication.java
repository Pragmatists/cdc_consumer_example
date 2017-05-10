package com.pragmatists.cdc;

import com.pragmatists.cdc.domain.Books;
import com.pragmatists.cdc.domain.BooksReader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(BooksReaderConfiguration.class)
public class ConsumerApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(ConsumerApplication.class, args);

        BooksReader booksReader = applicationContext.getBean(BooksReader.class);
        Books books = booksReader.all();

        System.out.println(String.format("Super we have read %d books:", books.books.size()));
    }
}
