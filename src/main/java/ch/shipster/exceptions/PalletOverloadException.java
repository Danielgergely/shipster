package ch.shipster.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Timo
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class PalletOverloadException extends RuntimeException {

    public PalletOverloadException(final String message) {
        super(message);
    }
}
