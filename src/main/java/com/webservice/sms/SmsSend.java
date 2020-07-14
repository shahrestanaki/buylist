package com.webservice.sms;

import com.exception.AppException;
import com.google.gson.Gson;
import com.tools.PublicValue;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SmsSend {
    private static volatile SmsSend smsSend = new SmsSend();

    public static SmsSend getInstance() {
        return smsSend;
    }

    public boolean sendSms(String mobile, String code, String templateId) {
        Map<String, Object> params = new HashMap<>();
        params.put("Parameter", "VerificationCode");
        params.put("ParameterValue", code);
        List<Map<String, Object>> ParameterArray = new ArrayList<>();
        ParameterArray.add(params);

        Map<String, Object> map = new HashMap<>();
        map.put("Mobile", mobile);
        map.put("TemplateId", templateId);
        map.put("ParameterArray", ParameterArray);
        Gson gson = new Gson();
        HttpEntity<String> request = new HttpEntity<>(gson.toJson(map), getHttpHeadersAuthorization(getToken()));
        try {
            SmsSendResult result = SmsWebService.getInstance().sendRequest(PublicValue.SMS_URL_SEND_SMS, request);
            if (result.getIsSuccessful()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("sms.general.sms");
        }
    }

    public String getToken() {
        return SmsWebService.getInstance().getToken();
    }

    private HttpHeaders getHttpHeadersAuthorization(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/json");
        headers.set("x-sms-ir-secure-token", token);
        return headers;
    }
}
