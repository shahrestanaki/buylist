package com.view;

import com.tools.Mobile;
import lombok.Data;

@Data
public class confirmSingupDto {
    @Mobile
    private String mobile;
    private String code;
}
