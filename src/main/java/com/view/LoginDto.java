package com.view;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tools.Mobile;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
public class LoginDto {
    @Mobile
    @NotNull
    private String mobile;

    @Length(min = 5, max = 20, message = "رمزعبور می تونه {min} تا {max} حرف داشته باشه")
    private String password;

    @JsonIgnore
    private String username;
}
