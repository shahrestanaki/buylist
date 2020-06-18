package com;

import com.view.SingUpDto;
import com.view.UserInfoView;
import com.webservice.WebConnect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

@SpringBootApplication
public class Application {
    public static void main(String... args) {
        SpringApplication.run(Application.class, args);
        ResponseEntity<String> test = WebConnect.getEntity("test/get");
        System.out.println("**********  " + test.getBody());


        String CORE_URL = "http://localhost:8080/core/";
        HttpHeaders headers = getHttpHeaders("");
        HttpEntity httpHeader = new HttpEntity(headers);
        UserInfoView user = WebConnect.getObject(CORE_URL + "test/getuser", HttpMethod.GET, httpHeader, UserInfoView.class);
        System.out.println("**********  " + user.getId());


        SingUpDto singUpDto = new SingUpDto();
        singUpDto.setUserName("usertest");
        HttpEntity<SingUpDto> request = new HttpEntity<>(singUpDto);
        user = WebConnect.getObject(CORE_URL + "test/postuser", HttpMethod.POST, request, UserInfoView.class);
        System.out.println("**********  " + user.getUserName());

        singUpDto = new SingUpDto();
        singUpDto.setUserName("usertest");
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1OTMwNjQyNDMsInVzZXJfbmFtZSI6Im1hbmFnZXIxIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9NQU5BR0UiXSwianRpIjoiMmU4YTE5ZmItZmI1Ny00ZjNlLThlNWQtYTBkN2MxMzYyNmMxIiwiY2xpZW50X2lkIjoiY2xpZW50SWQxIiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl19.NeEj8q9rrkRiH6xLszGB2LfNxOSFX3s_to62-nXc5pqJxboNPKWDRn3iNoWTl8W6PdcZsBahU1YsZcOt35UCzMVqQiJtEu27pxQFFLEQzPt27ewimaVLx3GCLS9dAbhuGVP1lPmnqOen5LB-Y9BH0lNvaFVx1iPH-eCvK6YcmvLJplGh3xeMlkvLYL4UjwvCJHDZv07qXvrPmC2hi29nDgoceGTKcFQOUc4vvh-qvNqdi7Zd0OoofFZvc7bnMKdV5QBl39qIzwj5s143EK8rp3Ik60NNxiHNRk8hoZXIAY776QriTEBpc8gapp94RytTBs08IE6w-OcJ8fWa7cuiMA";
        request = new HttpEntity<>(singUpDto, getHttpHeadersAuthorization(token));
        user = WebConnect.getObject(CORE_URL + "users/postuser", HttpMethod.POST, request, UserInfoView.class);
        System.out.println("**********  " + user.getUserName());

    }

    private static HttpHeaders getHttpHeaders(String bearerToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/json");
        // headers.set("Authorization", bearerToken);
        return headers;
    }

    private static HttpHeaders getHttpHeadersAuthorization(String bearerToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/json");
        headers.set("Authorization", "bearer " + bearerToken);
        return headers;
    }
}
