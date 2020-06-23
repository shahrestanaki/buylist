package com.repository;

import com.model.Users;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends GeneralRepository<Users, Long> {

    Users getByMobile(String mobile);

    Users getByUserName(String username);

}
