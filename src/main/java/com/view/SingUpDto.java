package com.view;

import com.tools.Mobile;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class SingUpDto {
    @Mobile
    private String mobile;
    @Length(min = 5, max = 20 , message = "لطفا برای رمز عبور بین {min} و {max} حرف وارد نمایید")
    private String password;
    @Length(min = 5, max = 20, message = "لطفا برای تکرار رمز عبور بین {min} و {max} حرف وارد نمایید")
    private String repeatPassword;

}
