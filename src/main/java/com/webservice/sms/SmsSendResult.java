package com.webservice.sms;

import com.view.Response;
import lombok.Data;

@Data
public class SmsSendResult extends Response {
    private String BatchKey;
    private Boolean IsSuccessful;
    private String TokenKey;
    private String Message;

    public SmsSendResult() {
    }
}
