package com.view;

import com.enump.MessageEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tools.EnglishAndNumber;
import com.tools.Mobile;
import com.tools.OnlyLetter;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class SendMessageDto {
    @Mobile
    private String mobile;

    @NotNull(message = "ارسال داده برای نوع پیام اجباری است")
    private MessageEnum type;

    @Pattern(regexp = OnlyLetter.PATTERN, message = "برای شرح پیام علائم ,.(),،_: رو به جز حرف های معمول قبول داریم")
    @Length(min = 4, max = 255, message = "شرح پبام باید حداقل {min} و حداکثر {max} حرف داشته باشه")
    @NotNull(message = "شرح پیام رو خالی می فرستی؟")
    private String comment;

    @JsonIgnore
    private String ip;
}
