package com.view;


import com.model.BaseEntity;
import com.tools.INumber;
import com.tools.OnlyLetter;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;


@Data
public class UsersUpdateView extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @Pattern(regexp = OnlyLetter.PATTERN,message = "برای نام کاربریت علائم ,.(),،_: رو به جز حرف های معمول قبول داریم")
    @Length(min = 3, max = 20, message = "برای نام کاربریت بین {min} و {max} حرف وارد کن")
    private String nickName;

    @Length(min = 1, max = 5, message = "برای عکس کاربرت بین {min} و {max} حرف وارد کن")
    @Pattern(regexp = INumber.PATTERN, message = "نوع داده وارد شده برای عکس کاربری باید عددی باشه")
    private String avatar;

}
