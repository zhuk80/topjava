package ru.javawebinar.topjava.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Evgeniy on 04.06.2017.
 */
@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY, reason = "nope")
public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super(message);
    }
}
