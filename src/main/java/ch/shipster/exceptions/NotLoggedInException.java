package ch.shipster.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Daniel
// https://www.baeldung.com/spring-boot-custom-error-page

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NotLoggedInException extends RuntimeException {

    public NotLoggedInException(final String message) {
        super(message);
    }
}
