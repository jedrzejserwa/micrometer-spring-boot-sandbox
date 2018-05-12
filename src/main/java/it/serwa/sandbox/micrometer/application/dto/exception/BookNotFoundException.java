package it.serwa.sandbox.micrometer.application.dto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(String isbn) {
        super("Book with isbn number: " + isbn + " has not been found in the library", null, false, false);
    }
}
