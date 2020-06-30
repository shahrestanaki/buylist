package com.enump;

import com.tools.PublicValue;
import com.view.Combo;

import java.util.ArrayList;
import java.util.List;

public enum UnitEnum {
    GRAM(PublicValue.GRAM),
    KILO(PublicValue.KILO),
    NUMBERS(PublicValue.NUMBERS);

    private String name;

    UnitEnum(String name) {
        this.name = name;
    }

    public static List<Combo> getList() {
        List<Combo> comboList = new ArrayList<>();
        for (UnitEnum unit : UnitEnum.values()) {
            comboList.add(new Combo(unit.name, unit.name()));
        }
        return comboList;
    }

    public String getName() {
        return name;
    }
}
