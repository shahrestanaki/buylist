package com.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        // http.authorizeRequests().antMatchers("/v2/api-docs", "/configuration/**", "/swagger-resources/**",  "/swagger-ui.html", "/webjars/**", "/api-docs/**").authenticated();
        http.authorizeRequests().antMatchers("/soap/**", "/register/**", "/approve/**", "/forget/**", "/cdn/**", "/cus1/**",
                "/hystrix.stream", "/swagger-ui.html", "/webjars/springfox-swagger-ui/**", "/configuration/ui",
                "/swagger-resources", "/v2/api-docs", "/swagger-resources/configuration/ui",
                "/swagger-resources/configuration/security", "/css/**", "/js/**", "/images/jcaptcha",
                "/templates/doc/**",
                "/oauth/token",
                "/user/sign-up",
                "/user/login",
                "/user/forget-password",
                "/user/confirm-singup",
                "/user/logout",
                "/general/**",
                "/message/send-message",
                "/message//load-by-ticket"
        ).permitAll();
        http.authorizeRequests().anyRequest().authenticated();
    }

}
