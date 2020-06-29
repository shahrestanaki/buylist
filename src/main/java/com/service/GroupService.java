package com.service;

import com.enump.UserGroupRoleEnum;
import com.googlecode.jmapper.JMapper;
import com.model.Group;
import com.model.UserGroup;
import com.repository.GroupRepository;
import com.service.search.SearchCriteriaList;
import com.service.search.UserGroupService;
import com.tools.GeneralTools;
import com.view.GroupView;
import com.view.SimplePageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupService extends GeneralService<Long, Group, GroupView> {

    @Autowired
    private GroupRepository groupRepo;
    JMapper<Group, GroupView> mapper = new JMapper<>(Group.class, GroupView.class);
    JMapper<GroupView, Group> mapperToView = new JMapper<>(GroupView.class, Group.class);

    @Autowired
    private UserGroupService userGroupSrv;

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

    public SimplePageResponse<GroupView> list(SearchCriteriaList search) {
        Group group = new Group();
        group.setCode("30SdP0HmQg");
        return groupRepo.list(group, mapperToView, search);
        /*Page page = groupRepo.list(group, search);
        List<Group> list = page.getContent();
        List<GroupView> listView = new ArrayList<>();
        list.forEach(item ->
                listView.add(mapperToView.getDestination(item))
        );
        return new SimplePageResponse<>(listView, page.getTotalElements());*/
    }
}
