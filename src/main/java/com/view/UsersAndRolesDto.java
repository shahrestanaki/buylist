package com.view;

import com.enump.UserGroupRoleEnum;
import lombok.Data;

@Data
public class UsersAndRolesDto {
    private long userId;
    private UserGroupRoleEnum role;
    private int roleStatus;
}
