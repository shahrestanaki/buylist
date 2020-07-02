package com.service;

import com.enump.UserGroupRoleEnum;
import com.exception.AppException;
import com.googlecode.jmapper.JMapper;
import com.model.Group;
import com.model.UserGroup;
import com.repository.UserGroupRepository;
import com.view.UserGroupView;
import com.view.UsersAndRolesDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserGroupService extends GeneralService<Long, UserGroup, UserGroupView> {
    JMapper<UserGroup, UserGroupView> mapper = new JMapper<>(UserGroup.class, UserGroupView.class);
    JMapper<UserGroupView, UserGroup> mapperToView = new JMapper<>(UserGroupView.class, UserGroup.class);

    @Autowired
    UserGroupRepository userGroupRepo;

    @Autowired
    GroupService groupSrv;

    public UserGroupView joinToGroup(String code) {
        Group group = groupSrv.findByCode(code);
        if (group == null) {
            throw new AppException("group.error.code.notFound");
        }
        UserGroup obj = new UserGroup();
        obj.setGroupId(group.getId());
        obj.setUserId(userSrv.getCurrentUser().getId());
        obj.setRole(UserGroupRoleEnum.normal);
        return mapperToView.getDestination(save(obj));
    }

    public UserGroup save(UserGroup userG) {
        return userGroupRepo.save(userG);
    }

    public UserGroup findByUserAndGroup(long userId, long groupId) {
        return userGroupRepo.findByUserIdAndGroupId(userId, groupId);
    }

    public boolean userIsAdminOfGroup(long userId, long groupId) {
        UserGroup userGroup = userGroupRepo.findByUserIdAndGroupId(userId, groupId);
        if (userGroup != null && userGroup.getRole().equals(UserGroupRoleEnum.Admin)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean userIsManagerOfGroup(long userId, long groupId) {
        UserGroup userGroup = userGroupRepo.findByUserIdAndGroupId(userId, groupId);
        if (userGroup.getRole().equals(UserGroupRoleEnum.Manager)) {
            return true;
        } else {
            return false;
        }
    }

    public List<UsersAndRolesDto> userRolesWithOutAdmin(long groupId) {
        return userGroupRepo.userRolesWithOutAdmin(groupId);
    }

    public UserGroup getAdmin(long groupId) {
        return userGroupRepo.userAdmin(groupId);
    }


    public void changeUsersRole(long groupId, List<UsersAndRolesDto> newRoles) {
        List<UsersAndRolesDto> oldRoles = userRolesWithOutAdmin(groupId);
        oldRoles.forEach(old ->
                newRoles.forEach(newr -> {
                    if (old.getUserId() == newr.getUserId() && old.getRole() != newr.getRole()) {
                        updateUserRole(groupId, old.getUserId(), newr.getRole());
                    }
                })
        );
    }


    public void changeAdmin(long groupId, long userId) {
        updateUserRole(groupId, userId, UserGroupRoleEnum.Admin);
    }

    public void updateUserRole(long groupId, long userId, UserGroupRoleEnum role) {
        userGroupRepo.updateUserRole(groupId, userId, role);
    }

    public List<UserGroup> getAllbyUserId(Long userId) {
        UserGroup obj = new UserGroup();
        obj.setUserId(userId);
        Example<UserGroup> ex = Example.of(obj);
        return userGroupRepo.findAll(ex);
    }
}
