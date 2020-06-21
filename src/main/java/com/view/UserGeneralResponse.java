package com.view;

import org.springframework.http.HttpStatus;

public class UserGeneralResponse extends Response {
    private String value;

    UserGeneralResponse() {

    }

    public UserGeneralResponse(HttpStatus result) {
        super.setResult(result);
    }

    public UserGeneralResponse(HttpStatus result, String value) {
        super.setResult(result);
        this.value = value;
    }
}
