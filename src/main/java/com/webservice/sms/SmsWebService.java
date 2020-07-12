package com.webservice.sms;

import com.exception.AppException;
import com.google.gson.Gson;
import com.tools.GetResourceBundle;
import com.tools.PublicValue;
import com.webservice.TokenHolder;
import com.webservice.WebConnect;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class SmsWebService {
    private static volatile SmsWebService smsToken = new SmsWebService();
    private Map<String, TokenHolder> cashSmsToken = new HashMap<>();
    private final String tokenName = "SMS_TOKEN";

    private SmsWebService() {
    }

    public static SmsWebService getInstance() {
        return smsToken;
    }

    public synchronized String getToken() {
        TokenHolder token = this.cashSmsToken.get(tokenName);
        if (token == null) {
            return this.createToken().getToken();
        } else if (System.currentTimeMillis() > token.getTime()) {
            TokenHolder tokenHolder = this.createToken();
            return tokenHolder.getToken();
        } else {
            return this.cashSmsToken.get(tokenName).getToken();
        }
    }

    private synchronized TokenHolder createToken() {
        String token = smsTokenLogin();

        TokenHolder tokenHolder = new TokenHolder(token);
        this.cashSmsToken.put("token", tokenHolder);
        return tokenHolder;
    }

    private String smsTokenLogin() {
        Map<String, String> map = new HashMap<>();
        map.put("UserApiKey", GetResourceBundle.getConfig.getString("sms.UserApiKey"));
        map.put("SecretKey", GetResourceBundle.getConfig.getString("sms.SecretKey"));
        HttpEntity<Map<String, String>> request = new HttpEntity<>(map, getHttpHeaders());

        SmsSendResult obj = sendRequest(PublicValue.SMS_URL_LOGIN, request);
        if (obj.getIsSuccessful()) {
            return obj.getTokenKey();
        } else {
            throw new AppException("sms.general.token");
        }
    }

    public SmsSendResult sendRequest(String url, HttpEntity request) {
        RestTemplate restTemplate;
        if (GetResourceBundle.getConfig.getString("proxy.use").equals("true")) {
            restTemplate = WebConnect.restTemplateProxy();
        } else {
            restTemplate = new RestTemplate();
        }
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            if (response.getStatusCode().equals(HttpStatus.CREATED)) {
                Gson gson = new Gson();
                SmsSendResult obj = gson.fromJson(response.getBody(), SmsSendResult.class);
                if (obj != null) {
                    System.out.println("***************** sms message : " + obj.getMessage() + " *******************");
                    return obj;
                }
            }
            throw new AppException("sms.general.error");
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("sms.general.error");
        }
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/json");
        return headers;
    }
}
