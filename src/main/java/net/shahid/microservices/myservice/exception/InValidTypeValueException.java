package net.shahid.microservices.myservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InValidTypeValueException extends RuntimeException {
    public InValidTypeValueException(String message) {
        super(message);
    }
}
