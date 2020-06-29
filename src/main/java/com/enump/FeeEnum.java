package com.enump;

import com.tools.PublicValue;
import com.view.Combo;

import java.util.ArrayList;
import java.util.List;

public enum FeeEnum {
    HEZAR_TOMAN(PublicValue.HEZAR_TOMAN),
    TOMAN(PublicValue.TOMAN),
    MILION(PublicValue.MILION);

    private String name;

    FeeEnum(String name) {
        this.name = name;
    }

    public static List<Combo> getList() {
        List<Combo> comboList = new ArrayList<>();
        for (FeeEnum fee : FeeEnum.values()) {
            comboList.add(new Combo(fee.name, fee.name()));
        }
        return comboList;
    }

}
