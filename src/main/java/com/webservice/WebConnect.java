package com.webservice;

import com.exception.AppException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class WebConnect {
    static String CORE_URL = "http://localhost:8080/core/";
    static RestTemplate restTemplate = new RestTemplate();

    public static ResponseEntity<String> getEntity(String addUrl) {
        ResponseEntity<String> response = restTemplate.getForEntity(CORE_URL + addUrl, String.class);
        checkResponse(response);
        return response;
    }

   /* public static <R> R getObject(String addUrl, HttpEntity httpHeader, Class<R> responseType) {
        ResponseEntity<R> responseEntity = null;
        try {
            responseEntity = restTemplate.exchange(
                    CORE_URL + addUrl, HttpMethod.GET, httpHeader, responseType);
        } catch (HttpClientErrorException | NullPointerException e) {
            e.printStackTrace();
        }
        return responseEntity != null ? responseEntity.getBody() : null;
    }*/

    public static <R> R getObject(String url, HttpMethod type, HttpEntity httpHeader, Class<R> responseType) {
        ResponseEntity<R> responseEntity = null;
        try {
            responseEntity = restTemplate.exchange(url, type, httpHeader, responseType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (responseEntity != null && responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            return responseEntity.getBody();
        } else {//TODO error
            return null;
        }

    }

   /* public static <T> T getObject(String addUrl) {
        T response = restTemplate.getForObject(CORE_URL + addUrl, null);
        //checkResponse(response);
        return response;
    }*/

    private static void checkResponse(ResponseEntity response) {
        if (!response.getStatusCode().equals(HttpStatus.OK)) {
            throw new AppException("error in get response");
        }
    }
}
