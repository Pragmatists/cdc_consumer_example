package com.pragmatists.cdc;

import com.pragmatists.cdc.domain.Book;
import org.assertj.core.api.AbstractAssert;

public class BookAssertion extends AbstractAssert<BookAssertion, Book> {
    public BookAssertion(Book actual) {
        super(actual,BookAssertion.class);

    }
    public static BookAssertion assertThat(Book actual) {
        return new BookAssertion(actual);
    }

    public BookAssertion hasTitle(String title){
        isNotNull();
        if(actual.title==null ||!actual.title.equals(title)){
            failWithMessage("Expected title %s but was %s",title,actual.title);
        }
        return this;
    }
    public BookAssertion hasAuthor(String author){
        isNotNull();
        if(actual.author==null ||!actual.author.equals(author)){
            failWithMessage("Expected author %s but was %s",author,actual.author);
        }
        return this;
    }
    public BookAssertion hasYear(int year){
        isNotNull();
        if(actual.year!=year){
            failWithMessage("Expected year %s but was %s",year,actual.year);
        }
        return this;
    }
    public BookAssertion hasId(String id){
        isNotNull();
        if(actual.id==null ||!actual.id.equals(id)){
            failWithMessage("Expected ID %s but was %s",id,actual.id);
        }
        return this;
    }
}
