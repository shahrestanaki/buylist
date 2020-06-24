package com.service;

import com.exception.AppException;
import com.tools.TokenRead;
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
        String buylistToken = CoreTokenManage.getInstance().getToken();
        HttpEntity<ForgetPasswordDto> request = new HttpEntity<>(new ForgetPasswordDto(userName), getHttpHeadersAuthorization(buylistToken));
        String result = WebConnect.getObject(CORE_URL + "users/forget-Password", HttpMethod.POST, request, String.class);
        if (result == null) {
            throw new AppException("core.general.error");
        }
        return result;
    }

    public void changePassword(ChangePasswordDto changePasswordDto) {
        String userToken = TokenRead.getToken();
        HttpEntity<ChangePasswordDto> request = new HttpEntity<>(new ChangePasswordDto(), getHttpHeadersAuthorization(userToken));
        WebConnect.getObject(CORE_URL + "users/change-Password", HttpMethod.POST, request, String.class);
    }

    public void logout() {
    }

    @Data
    private class singUpCore {
        private String userName;
        private String password;

        public singUpCore() {
        }


        public singUpCore(String userName, String password) {
            this.userName = userName;
            this.password = password;
        }
    }

    @Data
    private class ForgetPasswordDto {
        private String userName;

        public ForgetPasswordDto() {
        }

        public ForgetPasswordDto(String userName) {
            this.userName = userName;
        }
    }
}
