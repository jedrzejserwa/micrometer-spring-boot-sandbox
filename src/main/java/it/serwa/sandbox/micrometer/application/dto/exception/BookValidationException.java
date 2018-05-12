package it.serwa.sandbox.micrometer.application.dto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class BookValidationException extends RuntimeException {

    public BookValidationException() {
        super("Book validation exception", null, false, false);
    }
}
