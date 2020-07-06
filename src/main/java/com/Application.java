package com;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true) /*enable @PreAuthorize for roles*/
public class Application {
    public static void main(String... args) {
        SpringApplication.run(Application.class, args);
       /* ResponseEntity<String> test = WebConnect.getEntity("test/get");
        System.out.println("**********  " + test.getBody());


        String CORE_URL = "http://localhost:8080/core/";
        HttpHeaders headers = getHttpHeaders("");
        HttpEntity httpHeader = new HttpEntity(headers);
        UsersUpdateView user = WebConnect.getObject(CORE_URL + "test/getuser", HttpMethod.GET, httpHeader, UsersUpdateView.class);
        System.out.println("**********  " + user.getId());


        SingUpDto singUpDto = new SingUpDto();
        singUpDto.setUserName("usertest2");
        HttpEntity<SingUpDto> request = new HttpEntity<>(singUpDto);
        user = WebConnect.getObject(CORE_URL + "test/postuser", HttpMethod.POST, request, UsersUpdateView.class);
        System.out.println("**********  " + user.getUserName());

        singUpDto = new SingUpDto();
        singUpDto.setUserName("usertest");
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1OTMwNjQyNDMsInVzZXJfbmFtZSI6Im1hbmFnZXIxIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9NQU5BR0UiXSwianRpIjoiMmU4YTE5ZmItZmI1Ny00ZjNlLThlNWQtYTBkN2MxMzYyNmMxIiwiY2xpZW50X2lkIjoiY2xpZW50SWQxIiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl19.NeEj8q9rrkRiH6xLszGB2LfNxOSFX3s_to62-nXc5pqJxboNPKWDRn3iNoWTl8W6PdcZsBahU1YsZcOt35UCzMVqQiJtEu27pxQFFLEQzPt27ewimaVLx3GCLS9dAbhuGVP1lPmnqOen5LB-Y9BH0lNvaFVx1iPH-eCvK6YcmvLJplGh3xeMlkvLYL4UjwvCJHDZv07qXvrPmC2hi29nDgoceGTKcFQOUc4vvh-qvNqdi7Zd0OoofFZvc7bnMKdV5QBl39qIzwj5s143EK8rp3Ik60NNxiHNRk8hoZXIAY776QriTEBpc8gapp94RytTBs08IE6w-OcJ8fWa7cuiMA";
        request = new HttpEntity<>(singUpDto, getHttpHeadersAuthorization(token));
        user = WebConnect.getObject(CORE_URL + "users/postuser", HttpMethod.POST, request, UsersUpdateView.class);
        System.out.println("**********  " + user.getUserName());
*/
    }

    @Bean
    public ServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
            @Override
            protected void postProcessContext(Context context) {
                SecurityConstraint securityConstraint = new SecurityConstraint();
                securityConstraint.setUserConstraint("CONFIDENTIAL");
                SecurityCollection collection = new SecurityCollection();
                collection.addPattern("/*");
                securityConstraint.addCollection(collection);
                context.addConstraint(securityConstraint);
            }
        };
        tomcat.addAdditionalTomcatConnectors(redirectConnector());
        return tomcat;
    }

    private Connector redirectConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setScheme("http");
        connector.setPort(8080);
        connector.setSecure(false);
        connector.setRedirectPort(8443);
        return connector;
    }
}
