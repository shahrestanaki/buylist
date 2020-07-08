package com.view;

import com.model.BaseEntityView;
import com.tools.OnlyLetter;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class MessageView extends BaseEntityView {

    @Pattern(regexp = OnlyLetter.PATTERN, message = "برای شرح پیام علائم ,.(),،_: رو به جز حرف های معمول قبول داریم")
    @Length(min = 4, max = 500, message = "شرح پبام باید حداقل {min} و حداکثر {max} حرف داشته باشه")
    private String comment;

    private String answer;
}
