package com.enump;

import com.tools.PublicValue;
import com.view.Combo;

import java.util.ArrayList;
import java.util.List;

public enum MessageEnum {
    ERROR(PublicValue.MESSAGE_ERROR),
    COMMON(PublicValue.MESSAGE_COMMON),
    BUSINESS(PublicValue.MESSAGE_BUSINESS);

    private String name;

    MessageEnum(String name) {
        this.name = name;
    }

    public static List<Combo> getList() {
        List<Combo> comboList = new ArrayList<>();
        for (MessageEnum fee : MessageEnum.values()) {
            comboList.add(new Combo(fee.name, fee.name()));
        }
        return comboList;
    }

    public String getName() {
        return name;
    }
}
