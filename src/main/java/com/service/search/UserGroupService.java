package com.service.search;

import com.model.UserGroup;
import com.repository.UserGroupRepository;
import com.service.GeneralService;
import com.view.UserGroupView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserGroupService extends GeneralService<Long, UserGroup, UserGroupView> {

    @Autowired
    UserGroupRepository userGroupRepo;

    public UserGroupView create(UserGroupView view) {
        return mapperToView.getDestination(save(mapper.getDestination(view)));
    }

    public UserGroup save(UserGroup userG) {
        return userGroupRepo.save(userG);
    }
}
