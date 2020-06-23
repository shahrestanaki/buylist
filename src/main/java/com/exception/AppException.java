package com.exception;

import com.tools.GetResourceBundle;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;

import java.io.UnsupportedEncodingException;

public class AppException extends RuntimeException {
    @Autowired
    private Environment env;

    private static final long serialVersionUID = -7806029002430564887L;
    private String message;
    private HttpStatus httpStatus;

    public AppException(String message) {
        this.message = translate(message);
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }

    public AppException(String message, HttpStatus httpStatus) {
        this.message = translate(message);
        this.httpStatus = httpStatus;
    }

    public String translate(String source) {
        try {
            String temp = GetResourceBundle.getMessage_fa.getString(source);
            return new String(temp.getBytes("ISO-8859-1"), "UTF-8");
        } catch (NoClassDefFoundError | Exception e) {
            return source;
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}