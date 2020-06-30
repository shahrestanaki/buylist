package com.web;

import com.service.GroupService;
import com.service.search.SearchCriteriaList;
import com.view.GroupView;
import com.view.SimplePageResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @ApiOperation(value = "list of my group")
    @PostMapping("/list")
    public SimplePageResponse<GroupView> list(@Valid @RequestBody SearchCriteriaList search) {
        return groupService.list(search);
    }

    @ApiOperation(value = "get group invited code")
    @GetMapping("invite")
    public String inviteCode(@Valid @RequestParam(value = "id") long id) {
        return groupService.inviteCode(id);
    }

    @ApiOperation(value = "reset group code")
    @PostMapping("/reset-code")
    public void resetCode(@Valid @RequestParam(value = "id") long id) {
        groupService.resetCode(id);
    }

}
