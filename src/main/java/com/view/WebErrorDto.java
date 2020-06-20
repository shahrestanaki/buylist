package com.view;

import lombok.Data;

@Data
public class WebErrorDto {
    private String code;
    private String message;
    private String timestamp;
}
