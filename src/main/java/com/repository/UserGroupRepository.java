package com.repository;

import com.enump.UserGroupRoleEnum;
import com.model.UserGroup;
import com.view.UserGroupView;
import com.view.UsersAndRolesDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserGroupRepository extends GeneralRepository<UserGroup, UserGroupView, Long> {
    UserGroup findByUserIdAndGroupId(long userId, long groupId);

    @Query(value = "select ug.userId as userId, ug.role as role from UserGroup ug where ug.id = ?1 and ug.role <> 'Admin'")
    List<UsersAndRolesDto> userRolesWithOutAdmin(long groupId);

    @Query(value = "select ug from UserGroup ug where ug.id = ?1 and ug.role = 'Admin'")
    UserGroup userAdmin(long groupId);

    @Query(value = "update UserGroup set role = ?3 where groupId = ?1 and userId = ?2")
    void updateUserRole(long groupId, long userId, UserGroupRoleEnum role);
}
