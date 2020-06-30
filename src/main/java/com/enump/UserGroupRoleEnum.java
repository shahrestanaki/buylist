package com.enump;

import com.tools.PublicValue;
import com.view.Combo;

import java.util.ArrayList;
import java.util.List;

public enum UserGroupRoleEnum {
    normal(PublicValue.NORMAL),
    Admin(PublicValue.ADMIN),
    Manager(PublicValue.MANAGER);


    private String name;

    UserGroupRoleEnum(String name) {
        this.name = name;
    }

    public static List<Combo> getList() {
        List<Combo> comboList = new ArrayList<>();
        for (UserGroupRoleEnum fee : UserGroupRoleEnum.values()) {
            comboList.add(new Combo(fee.name, fee.name()));
        }
        return comboList;
    }

}
