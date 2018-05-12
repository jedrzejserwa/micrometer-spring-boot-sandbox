package it.serwa.sandbox.micrometer.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.io.Serializable;


@Getter
public class BookDTO implements Serializable {
    private static final long serialVersionUID = -6538633534569958263L;

    private final String author;

    @JsonProperty("name")
    private final String bookName;

    @JsonProperty("isbn")
    private final String bookISBN;

    private BookDTO() {
        // Jackson deserialization
        this.author = "";
        this.bookName = "";
        this.bookISBN = "";
    }

    public BookDTO(String author, String bookName, String bookISBN) {
        this.author = author;
        this.bookName = bookName;
        this.bookISBN = bookISBN;
    }
}
