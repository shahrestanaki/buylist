package com.view;

import com.tools.CorrectDate;
import lombok.Data;

import java.util.Date;

@Data
public class UserLoginResponse extends UserGeneralResponse {
    private String token;
    private String refresh_token;
    private Date time;
    private String timstamp;

    public void setTimstamp(String timstamp) {
        this.timstamp = timstamp;
        this.time = CorrectDate.addToDate(new Date(), Long.valueOf(timstamp));
    }
}
