package com.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "")
public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException() {
    }
}
