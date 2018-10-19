package com.pragmatists.cdc;

import com.pragmatists.cdc.domain.Book;
import org.assertj.core.api.Assertions;

public class ProjectAssertions extends Assertions {
    public static BookAssertion assertThat(Book book) {
        return BookAssertion.assertThat(book);
    }
}
