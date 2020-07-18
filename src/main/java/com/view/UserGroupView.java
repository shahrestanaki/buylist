package com.view;

import com.enump.UserGroupRoleEnum;
import com.model.BaseEntityView;
import lombok.Data;

@Data
public class UserGroupView extends BaseEntityView {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long groupId;

    private Long userId;

    private String nickName;

    private String avatar;

    private UserGroupRoleEnum role;
    private String roleName;

}
