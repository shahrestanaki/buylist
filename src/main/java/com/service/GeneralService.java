package com.service;

import com.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor = Exception.class)
public class GeneralService<Pk, M, V> {

    @Autowired
    UserService userSrv;

    public Users getUser() {
        return userSrv.getCurrentUser();
    }
}
