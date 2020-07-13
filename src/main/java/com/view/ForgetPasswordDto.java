package com.view;

import com.tools.Mobile;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class ForgetPasswordDto {
    @Mobile
    @NotNull
    private String mobile;

    @NotNull
    private Date singUpDate;
}
