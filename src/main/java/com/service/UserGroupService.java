package com.service;

import com.enump.UserGroupRoleEnum;
import com.exception.AppException;
import com.googlecode.jmapper.JMapper;
import com.model.Group;
import com.model.UserGroup;
import com.repository.UserGroupRepository;
import com.view.UserGroupView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
