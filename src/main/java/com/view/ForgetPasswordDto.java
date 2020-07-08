package com.view;

import com.tools.Mobile;
import com.tools.UserName;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
public class ForgetPasswordDto {
    @Mobile
    @NotNull
    private String mobile;
}
