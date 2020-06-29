package com.view;

import com.enump.UserGroupRoleEnum;
import com.googlecode.jmapper.annotations.JMap;
import com.model.BaseEntityView;
import lombok.Data;

@Data
public class UserGroupView extends BaseEntityView {
    private static final long serialVersionUID = 1L;
    @JMap
    private Long id;
    @JMap
    private Long groupId;
    @JMap
    private Long userId;
    @JMap
    private String nickName;
    @JMap
    private String avatar;
    @JMap
    private UserGroupRoleEnum role;
    private String roleName;

}
