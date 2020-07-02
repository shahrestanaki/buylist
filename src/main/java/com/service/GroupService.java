package com.service;

import com.enump.UserGroupRoleEnum;
import com.exception.AppException;
import com.googlecode.jmapper.JMapper;
import com.model.Group;
import com.model.UserGroup;
import com.repository.GroupRepository;
import com.service.search.GoodsSearch;
import com.tools.GeneralTools;
import com.view.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupService extends GeneralService<Long, Group, GroupView> {

    @Autowired
    private GroupRepository groupRepo;
    JMapper<Group, GroupView> mapper = new JMapper<>(Group.class, GroupView.class);
    JMapper<GroupView, Group> mapperToView = new JMapper<>(GroupView.class, Group.class);

    @Autowired
    private UserGroupService userGroupSrv;

    @Autowired
    private GoodsService goodsService;

    public GroupView create(GroupView view) {
        return mapperToView.getDestination(save(mapper.getDestination(view)));
    }

    public Group save(Group group) {
        group.setId(null);
        group.setCode(GeneralTools.createRandom("all", 10));
        groupRepo.save(group);

        UserGroup userGroup = new UserGroup(group.getId(), getUser().getId(), UserGroupRoleEnum.Admin);
        userGroupSrv.save(userGroup);

        return group;
    }

    public List<GroupView> list() {
        List<UserGroup> ulist = userGroupSrv.getAllbyUserId(getUser().getId());
        List<GroupView> list = new ArrayList<>();
        ulist.forEach(item ->
                list.add(mapperToView.getDestination(item.getGroup()))
        );
        return list;
    }

    public Group findByCode(String code) {
        return groupRepo.findByCode(code);
    }

    public String inviteCode(long groupId) {
        return securityGetGroup(groupId).getCode();
    }

    public void resetCode(long groupId) {
        Group group = securityGetGroup(groupId);
        group.setCode(GeneralTools.createRandom("all", 10));
        groupRepo.save(group);
    }

    public Group securityGetGroup(long groupId) {
        UserGroup userG = userGroupSrv.findByUserAndGroup(getUser().getId(), groupId);
        if (userG == null) {
            throw new AppException("security.check.conflict");
        }
        return userG.getGroup();
    }

    public UserGroup securityGetUserGroup(long groupId) {
        UserGroup userG = userGroupSrv.findByUserAndGroup(getUser().getId(), groupId);
        if (userG == null) {
            throw new AppException("security.check.conflict");
        }
        return userG;
    }

    public GroupDetailsDto groupDetails(long groupId) {
        Group group = securityGetGroup(groupId);
        GoodsSearch goodsSearch = new GoodsSearch(1, 5, "id", groupId);
        SimplePageResponse<GoodsView> goodsList = goodsService.list(goodsSearch);
        boolean isAdmin = false;
        boolean isManager = false;
        UserGroup ug = getUserGroup(groupId);
        if (ug.getRole().equals(UserGroupRoleEnum.Admin)) {
            isAdmin = true;
        } else if (ug.getRole().equals(UserGroupRoleEnum.Manager)) {
            isManager = true;
        }
        return new GroupDetailsDto(group, goodsList, isAdmin, isManager);
    }

    public GroupUpdateDto editInfo(long groupId) {
        UserGroup userG = securityGetUserGroup(groupId);
        if (userG.getRole().equals(UserGroupRoleEnum.normal)) {
            return new GroupUpdateDto(groupId, userG.getNickName(), userG.getAvatar());
        }
        Group group = groupRepo.getOne(groupId);
        List<UsersAndRolesDto> userRoles = userRolesWithOutAdmin(groupId);
        return new GroupUpdateDto(userG.getNickName(), userG.getAvatar(),
                groupId, group.getName(), group.getAbout(), group.getAbout(), userRoles);
    }

    public List<UsersAndRolesDto> userRolesWithOutAdmin(long groupId) {
        List<UsersAndRolesDto> list = userGroupSrv.userRolesWithOutAdmin(groupId);
        list.forEach(item -> {
            if (item.getRole().equals(UserGroupRoleEnum.Manager)) {
                item.setRoleStatus(1);
            } else {
                item.setRoleStatus(0);
            }
        });
        return list;
    }


    public void update(GroupUpdateDto view) {
        UserGroup userG = securityGetUserGroup(view.getGroupId());
        userG.setNickName(view.getUserNickName());
        userG.setAvatar(view.getUserAvatar());

        if (userG.getRole().equals(UserGroupRoleEnum.Admin) || userG.getRole().equals(UserGroupRoleEnum.Manager)) {
            Group group = userG.getGroup();
            group.setName(view.getGroupName());
            group.setAvatar(view.getGroupAvatar());
            group.setAbout(view.getGroupAbout());

            userGroupSrv.changeUsersRole(view.getGroupId(), view.getUserRoles());
        }
        if (userG.getRole().equals(UserGroupRoleEnum.Admin)) {
            if (view.getAdmin() != null || !view.getAdmin().equals(getUserGroup(view.getGroupId()).getId())) {
                userG.setRole(UserGroupRoleEnum.normal);
                userGroupSrv.changeAdmin(view.getGroupId(), view.getAdmin());
            }
        }
        userGroupSrv.save(userG);
    }

}
