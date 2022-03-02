package shipster.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NoPermissionException extends RuntimeException {

    public NoPermissionException(final String message) {
        super(message);
    }
}
