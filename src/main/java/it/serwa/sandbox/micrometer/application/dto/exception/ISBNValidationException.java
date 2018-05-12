package it.serwa.sandbox.micrometer.application.dto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class ISBNValidationException extends RuntimeException {

    public ISBNValidationException() {
        super("ISBN can not be null or empty", null, false, false);
    }
}
