package com.enump;

import com.tools.PublicValue;
import com.view.Combo;

import java.util.ArrayList;
import java.util.List;

public enum SmsTypeEnum {
    LOGIN(PublicValue.HEZAR_TOMAN),
    FORGET_PASSWORD(PublicValue.TOMAN);

    private String name;

    SmsTypeEnum(String name) {
        this.name = name;
    }

    public static List<Combo> getList() {
        List<Combo> comboList = new ArrayList<>();
        for (SmsTypeEnum fee : SmsTypeEnum.values()) {
            comboList.add(new Combo(fee.name, fee.name()));
        }
        return comboList;
    }

    public String getName() {
        return name;
    }
}
