package it.serwa.sandbox.micrometer.application.dto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class BookAlreadyBorrowedException extends RuntimeException {

    public BookAlreadyBorrowedException(String isbn) {
        super("Book with isbn number: " + isbn + " is already borrowed", null, false, false);
    }
}
