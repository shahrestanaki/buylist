package com.view;

import com.tools.Mobile;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class SingUpDto {
    @Mobile
    private String mobile;
    @Length(min = 5, max = 20)
    private String password;
    @Length(min = 5, max = 20)
    private String repeatPassword;

}
