package com.view;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class ChangePasswordDto {

    @Length(min = 5, max = 20, message = "رمزعبور قدیمی باید حداقل {min} و حداکثر {max} حرف داشته باشه")
    private String oldPassword;

    @Length(min = 5, max = 20, message = "رمزعبور جدید باید حداقل {min} و حداکثر {max} حرف داشته باشه")
    private String newPassword;

    @Length(min = 5, max = 20, message = "تکرار رمزعبور باید حداقل {min} و حداکثر {max} حرف داشته باشه")
    private String repeatPassword;

}
