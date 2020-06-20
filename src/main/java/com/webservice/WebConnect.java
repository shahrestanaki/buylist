package com.webservice;

import com.exception.AppException;
import com.google.gson.Gson;
import com.view.WebErrorDto;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

public class WebConnect {

    private static HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/json");
        return headers;
    }

    private static HttpHeaders getHttpHeadersAuthorization(String bearerToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/json");
        headers.set("Authorization", "bearer " + bearerToken);
        return headers;
    }

    static RestTemplate restTemplate = new RestTemplate();

    /*public static ResponseEntity<String> getEntity(String url) {
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            checkResponse(response);
            return response;
        } catch (Exception e) {
            handelWebError(e);
        }
        return null;
    }*/

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
            handelWebError(e);
        }
        if (responseEntity != null && responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            return responseEntity.getBody();
        } else {
            return null;
        }
    }

    private static void checkResponse(ResponseEntity response) {
        if (!response.getStatusCode().equals(HttpStatus.OK)) {
            throw new AppException("error in get response");
        }
    }


    private static void handelWebError(Exception e) {
        e.printStackTrace();

        String error = ((HttpClientErrorException.BadRequest) e).getResponseBodyAsString();
        if (error.contains("core_code")) {
            Gson gson = new Gson();
            WebErrorDto obj = gson.fromJson(error, WebErrorDto.class);
            System.out.println(error);
            throw new AppException("core." + obj.getMessage().replaceAll("\\ ", "\\."));
        } else {
            throw new AppException("core.general.error");
        }
    }
}
