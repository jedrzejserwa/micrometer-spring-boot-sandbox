package it.serwa.sandbox.micrometer.application.dto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class SavingBookException extends RuntimeException {

    public SavingBookException(String isbn) {
        super("Error while saving book with isbn number: " + isbn + " into db", null, false, false);
    }
}
