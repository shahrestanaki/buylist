package com.view;

import com.tools.Mobile;
import lombok.Data;

@Data
public class ChangeMobileDto {
    @Mobile
    private String mobile;
}
