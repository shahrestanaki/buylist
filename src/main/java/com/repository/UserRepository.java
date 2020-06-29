package com.repository;

import com.model.Users;
import com.view.UsersView;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends GeneralRepository<Users, UsersView, Long> {

    Users getByMobile(String mobile);

    Users getByUserName(String username);

}
