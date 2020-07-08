package com.view;

import com.tools.Mobile;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
public class SingUpDto {
    @Mobile
    @NotNull
    private String mobile;

    @Length(min = 5, max = 20 , message = "رمزعبور می تونه {min} تا {max} حرف داشته باشه")
    private String password;

    @Length(min = 5, max = 20, message = "تکرار رمزعبور می تونه {min} تا {max} حرف داشته باشه")
    private String repeatPassword;

}
