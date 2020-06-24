package com.tools;

import com.exception.AppException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class TokenRead {
    public static String getUserName() {
        try {
            Authentication authentication = SecurityContextHolder.getContext()
                    .getAuthentication();
            String username = (String) authentication.getPrincipal();
            return  username;
        } catch (Exception exp) {
            exp.printStackTrace();
            throw new AppException("general.error");
        }
    }

    public static Collection<? extends GrantedAuthority> getRols() {
        try {
            Authentication authentication = SecurityContextHolder.getContext()
                    .getAuthentication();

            return authentication.getAuthorities();
        } catch (Exception exp) {
            throw new AppException("token.error.getRols");
        }
    }

    public static String getToken() {
        try {
            Authentication authentication = SecurityContextHolder.getContext()
                    .getAuthentication();

            return ((OAuth2AuthenticationDetails)authentication.getDetails()).getTokenValue();
        } catch (Exception exp) {
            throw new AppException("token.error.Token");
        }
    }


    /**management*/
  /*  public static String getClientId() {
        try {
            Authentication authentication = SecurityContextHolder.getContext()
                    .getAuthentication();
            return ((OAuth2Authentication) authentication).getOAuth2Request().getClientId();
        } catch (Exception exp) {
            exp.printStackTrace();
            throw new AppException("token.error.getManagement");
        }
    }*/
}
