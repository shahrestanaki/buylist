package com.repository;

import com.model.Group;
import com.model.UserGroup;
import com.view.GroupView;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends GeneralRepository<Group, GroupView, Long> {

}
