package com.view;

import com.tools.Mobile;
import com.tools.UserName;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class ForgetPasswordDto {
    @Mobile
    private String mobile;
}
