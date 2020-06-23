package com.webservice;

import lombok.Data;

@Data
public class TokenHolder {
    private String token;
    private Long time;

    public TokenHolder(String token) {
        this.token = token;
        this.time = null;//TODO
    }

}