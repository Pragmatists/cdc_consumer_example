package com.pragmatists.cdc;

import com.pragmatists.cdc.domain.Book;
import com.pragmatists.cdc.ui.BooksReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

@SpringBootApplication
public class ConsumerApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(ConsumerApplication.class, args);

        BooksReader booksReader = applicationContext.getBean(BooksReader.class);
        List<Book> books = booksReader.all();

        System.out.println(String.format("Super we have read %d books", books.size()));
        System.out.println("With titles:");
        books.stream().forEach(book -> {
            System.out.println("\t - " + book.title);
        });
    }
}
