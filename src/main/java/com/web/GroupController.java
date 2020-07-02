package com.web;

import com.service.GroupService;
import com.service.search.SearchCriteriaList;
import com.view.GroupDetailsDto;
import com.view.GroupUpdateDto;
import com.view.GroupView;
import com.view.SimplePageResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(value = "list of my group")
    @PostMapping("/list")
    public List<GroupView> list() {
        return groupService.list();
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

    @ApiOperation(value = "get group detail")
    @GetMapping("groupDetails")
    public GroupDetailsDto groupDetails(@Valid @RequestParam(value = "id") long id) {
        return groupService.groupDetails(id);
    }

    @ApiOperation(value = "get group info for edit")
    @GetMapping("edit-info")
    public GroupUpdateDto editInfo(@Valid @RequestParam(value = "id") long id) {
        return groupService.editInfo(id);
    }

    @ApiOperation(value = "update group only by admin and managements")
    @PostMapping("/update")
    public void update(@Valid @RequestBody GroupUpdateDto view) {
        groupService.update(view);
    }

}
