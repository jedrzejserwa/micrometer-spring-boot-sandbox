package it.serwa.sandbox.micrometer.application.dto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class BookNotRentedException extends RuntimeException {

    public BookNotRentedException(String isbn) {
        super("Book with isbn number: " + isbn + " has not been rented", null, false, false);
    }
}
