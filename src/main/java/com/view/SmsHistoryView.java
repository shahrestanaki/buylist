package com.view;

import com.enump.SmsTypeEnum;
import com.model.BaseEntityView;
import lombok.Data;

@Data
public class SmsHistoryView extends BaseEntityView {
    private Long id;
    private Long userId;
    private SmsTypeEnum smsType;
    private String mobile;
    private String message;
    private Long dateStr;
}
