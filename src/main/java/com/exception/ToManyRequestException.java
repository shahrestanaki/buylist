package com.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.TOO_MANY_REQUESTS)
public class ToManyRequestException extends RuntimeException {

    public ToManyRequestException(String message) {
        super(message);
    }

    public ToManyRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
