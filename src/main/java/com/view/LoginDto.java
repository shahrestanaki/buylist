package com.view;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tools.Mobile;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class LoginDto {
    @Mobile
    private String mobile;
    @Length(min = 5, max = 20)
    private String password;

    @JsonIgnore
    private String username;
}
