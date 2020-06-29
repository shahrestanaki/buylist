package com.web;

import com.service.GroupService;
import com.service.search.SearchCriteriaList;
import com.view.GroupView;
import com.view.SimplePageResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/group")
@Api(value = "group")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @ApiOperation(value = "create group")
    @PostMapping("/create")
    public GroupView create(@Valid @RequestBody GroupView view) {
        return groupService.create(view);
    }

    @ApiOperation(value = "create group")
    @PostMapping("/list")
    public SimplePageResponse<GroupView> list(@Valid @RequestBody SearchCriteriaList search) {
        return groupService.list(search);
    }

}
