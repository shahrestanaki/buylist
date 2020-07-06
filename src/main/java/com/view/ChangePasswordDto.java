package com.view;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class ChangePasswordDto {

    @Length(min = 5, max = 20, message = "لطفا برای رمزعبور قدیمی بین {min} و {max} حرف وارد نمایید")
    private String oldPassword;
    @Length(min = 5, max = 20, message = "لطفا برای رمزعبور جدید بین {min} و {max} حرف وارد نمایید")
    private String newPassword;
    @Length(min = 5, max = 20, message = "لطفا برای تکرار رمزعبور بین {min} و {max} حرف وارد نمایید")
    private String repeatPassword;

}
