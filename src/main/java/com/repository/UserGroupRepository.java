package com.repository;

import com.model.UserGroup;
import com.view.UserGroupView;
import org.springframework.stereotype.Repository;

@Repository
public interface UserGroupRepository extends GeneralRepository<UserGroup, UserGroupView, Long> {

}
