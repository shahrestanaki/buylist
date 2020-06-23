package com.view;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CoreLoginRequest {

    private static final String GRANT_TYPE = "password";

    @JsonProperty("grant_type")
    private String grantType;

    private String username;
    private String password;

    public CoreLoginRequest() {
        this.grantType = GRANT_TYPE;
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
