package com.view;

import com.tools.Mobile;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ChangeMobileDto {
    @Mobile
    @NotNull
    private String mobile;
}
