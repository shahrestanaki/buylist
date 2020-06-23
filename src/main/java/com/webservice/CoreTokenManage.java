package com.webservice;


import com.tools.PublicValue;
import com.view.CoreLoginRequest;
import com.view.CoreLoginResponse;

import java.util.HashMap;
import java.util.Map;

public class CoreTokenManage {

    private static volatile CoreTokenManage coreTokenManage = new CoreTokenManage();
    private Map<String, TokenHolder> cashToken = new HashMap<>();//new ConcurrentHashMap();
    private final String tokenName = "TOKEN";

    private CoreTokenManage() {
    }

    public static CoreTokenManage getInstance() {
        return coreTokenManage;
    }

    public synchronized String getToken() {
        TokenHolder token = this.cashToken.get(tokenName);
        if (token == null) {
            return this.createToken().getToken();
        } else if (System.currentTimeMillis() > token.getTime()) {
            TokenHolder tokenHolder = this.createToken();
            return tokenHolder.getToken();
        } else {
            return this.cashToken.get(tokenName).getToken();
        }
    }

    private synchronized TokenHolder createToken() {
        String token = buyListLogin();

        TokenHolder tokenHolder = new TokenHolder(token);
        this.cashToken.put("token", tokenHolder);
        return tokenHolder;
    }

    public String buyListLogin() {
        CoreLoginRequest loginRequest = new CoreLoginRequest();
        loginRequest.setUsername(PublicValue.CORE_USER);
        loginRequest.setPassword(PublicValue.CORE_PASSWORD);
        CoreLoginResponse response = WebConnect.loginCore(loginRequest);
        //TODO for error
        return response.getAccess_token();

    }
}


