package com.service;

import com.model.UserGroup;
import com.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor = Exception.class)
public class GeneralService<Pk, M, V> {

    @Autowired
    UserService userSrv;

    @Autowired
    UserGroupService userGroupSrv;

    public Users getUser() {
        return userSrv.getCurrentUser();
    }

    public UserGroup getUserGroup(Long groupId) {
        return userGroupSrv.findByUserAndGroup(getUser().getId(), groupId);
    }
}
