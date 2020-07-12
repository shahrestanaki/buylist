package com.webservice;

import com.exception.AppException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.tools.GeneralTools;
import com.tools.GetResourceBundle;
import com.tools.PublicValue;
import com.view.CoreLoginRequest;
import com.view.CoreLoginResponse;
import com.view.WebErrorDto;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.Charset;
import java.util.HashMap;

public class WebConnect {
    static RestTemplate restTemplate = new RestTemplate();

    public static CoreLoginResponse loginCore(CoreLoginRequest loginRequest) {
        HttpHeaders headers = createLoginHeaders(PublicValue.CORE_CLIENT_ID, PublicValue.CORE_SECRET);
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(PublicValue.CORE_URL_LOGIN);
        setQueryParam(loginRequest, builder);
        String loginUrl = builder.build().toUriString();
        CoreLoginResponse loginResponse = null;
        try {
            ResponseEntity<CoreLoginResponse> response = restTemplate.exchange(
                    loginUrl,
                    HttpMethod.POST,
                    new HttpEntity<>(headers),
                    CoreLoginResponse.class);
            loginResponse = response.getBody();
            loginResponse.setResult(response.getStatusCode());
        } catch (Exception ex) {
            handelWebError(ex);
        }
        return loginResponse;
    }

    private static HttpHeaders createLoginHeaders(final String username, final String password) {
        return new HttpHeaders() {{
            String auth = username + ":" + password;
            byte[] encodedAuth = Base64.encodeBase64(
                    auth.getBytes(Charset.forName("US-ASCII")));
            String authHeader = "Basic " + new String(encodedAuth);
            set("Authorization", authHeader);
            setContentType(MediaType.APPLICATION_JSON);
        }};
    }

    private static <T extends Object> UriComponentsBuilder setQueryParam(T object, UriComponentsBuilder builder) {
        ObjectMapper mapper = new ObjectMapper();
        HashMap data = mapper.convertValue(object, HashMap.class);
        for (Object key : data.keySet()) {
            builder.queryParam(key.toString(), data.get(key));
        }
        return builder;
    }

    public static <R> R getObjectWithProxy(String url, HttpMethod type, HttpEntity httpHeader, Class<R> responseType) {
        if (GetResourceBundle.getConfig.getString("proxy.use").equals("true")) {
            return getObjectGeneral(url, type, httpHeader, responseType, restTemplateProxy());
        } else {
            return getObjectGeneral(url, type, httpHeader, responseType, restTemplate);
        }
    }

    public static <R> R getObject(String url, HttpMethod type, HttpEntity httpHeader, Class<R> responseType) {
        return getObjectGeneral(url, type, httpHeader, responseType, restTemplate);
    }

    private static <R> R getObjectGeneral(String url, HttpMethod type, HttpEntity httpHeader, Class<R> responseType, RestTemplate restTemp) {
        ResponseEntity<R> responseEntity = null;
        try {
            responseEntity = restTemp.exchange(url, type, httpHeader, responseType);
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

    private static void handelWebError(Exception e) {//TODO log for users
        e.printStackTrace();

        String message = "";
        String error = "";
        if (e.toString().contains("500")) {
            message = ((HttpServerErrorException.InternalServerError) e).getResponseBodyAsString();
        } else if (e.toString().contains("401")) {
            message = ((HttpClientErrorException.Unauthorized) e).getResponseHeaders().get("WWW-Authenticate").get(0);
            if (message.contains("User is Locked")) {
                error = "core.user.islock";
            } else if (message.contains("User not Exist")) {
                error = "core.user.not.found";
            } else if (message.contains("User Deactivate")) {
                error = "core.user.Deactivate";
            }
        } else if (e.toString().contains("404")) {
            message = ((HttpClientErrorException.NotFound) e).getResponseBodyAsString();
        } else if (e.toString().contains("400")) {
            message = ((HttpClientErrorException.BadRequest) e).getResponseBodyAsString();
            if (message.contains("Bad credentials")) {
                error = "core.message.login." + GeneralTools.createRandom(1, 3);
            }
        }

        if (message.contains("core_code")) {
            Gson gson = new Gson();
            WebErrorDto obj = gson.fromJson(message, WebErrorDto.class);
            System.out.println(message);
            error = "core." + obj.getMessage().replaceAll("\\ ", "\\.");
        } else if (message.equals("") || error.equals("")) {
            error = "core.general.error";
        }
        System.out.println("*** - message : " + message);
        throw new AppException(error);
    }

    public static RestTemplate restTemplateProxy() {
        final String username = GetResourceBundle.getConfig.getString("proxy.username");
        final String password = GetResourceBundle.getConfig.getString("proxy.password");
        final String proxyUrl = GetResourceBundle.getConfig.getString("proxy.url");
        final int port = Integer.valueOf(GetResourceBundle.getConfig.getString("proxy.port"));

        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
                new AuthScope(proxyUrl, port),
                new UsernamePasswordCredentials(username, password)
        );

        HttpHost myProxy = new HttpHost(proxyUrl, port);
        HttpClientBuilder clientBuilder = HttpClientBuilder.create();

        clientBuilder.setProxy(myProxy).setDefaultCredentialsProvider(credsProvider).disableCookieManagement();

        HttpClient httpClient = clientBuilder.build();
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setHttpClient(httpClient);

        return new RestTemplate(factory);
    }


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
}
