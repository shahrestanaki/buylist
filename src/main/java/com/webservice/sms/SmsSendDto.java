package com.webservice.sms;

import com.tools.GetResourceBundle;
import lombok.Data;

@Data
public class SmsSendDto {
    private String Messages;
    private String MobileNumbers;
    private String LineNumber = GetResourceBundle.getConfig.getString("sms.number");
    private String SendDateTime;
    private String CanContinueInCaseOfError = "false";

    public SmsSendDto(){}

    public SmsSendDto(String messages, String mobileNumbers) {
        Messages = messages;
        MobileNumbers = mobileNumbers;
        this.SendDateTime = "";
    }
}
