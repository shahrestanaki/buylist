package com.service;

import com.exception.AppException;
import com.view.*;
import com.webservice.CoreTokenManage;
import com.webservice.WebConnect;
import lombok.Data;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class WebServerService {
    private static final String CORE_URL = "http://localhost:8080/core/";

    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/json");
        return headers;
    }

    private HttpHeaders getHttpHeadersAuthorization(String bearerToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/json");
        headers.set("Authorization", "bearer " + bearerToken);
        return headers;
    }

    public void singUpUser(String username, String password) {
        String buylistToken = CoreTokenManage.getInstance().getToken();
        HttpEntity<singUpCore> request = new HttpEntity<>(new singUpCore(username, password), getHttpHeadersAuthorization(buylistToken));
        UserGeneralResponse result = WebConnect.getObject(CORE_URL + "users/sing-up", HttpMethod.POST, request, UserGeneralResponse.class);
        if (result == null) {
            throw new AppException("core.general.error");
        }
    }

    public ResponseEntity<UserLoginResponse> userLogin(LoginDto loginDto) {
        CoreLoginRequest loginRequest = new CoreLoginRequest();
        loginRequest.setUsername(loginDto.getUsername());
        loginRequest.setPassword(loginDto.getPassword());
        CoreLoginResponse response = WebConnect.loginCore(loginRequest);

        UserLoginResponse response1 = new UserLoginResponse();
        response1.setResult(response.getResult());
        response1.setToken(response.getAccess_token());
        response1.setRefresh_token(response.getRefresh_token());
        response1.setTimstamp(response.getExpires_in());
        ///------------------- and pore body //TODO
        return ResponseEntity.status(response1.getResult())
                .body(response1);
    }

    public String forgetPassword(String userName) {
        return null;
    }

    public void changePassword(ChangePasswordDto changePasswordDto) {
    }

    public void logout() {
    }

    @Data
    private class singUpCore {
        private String userName;
        private String password;

        public singUpCore() {
        }

        ;

        public singUpCore(String userName, String password) {
            this.userName = userName;
            this.password = password;
        }
    }
}
