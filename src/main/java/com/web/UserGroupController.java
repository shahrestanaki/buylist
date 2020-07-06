package com.web;

import com.service.UserGroupService;
import com.tools.EnglishAndNumber;
import com.view.UserGroupView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/usergroup")
@Api(value = "usergroup")
public class UserGroupController {

    @Autowired
    private UserGroupService userGroupService;

    @ApiOperation(value = "create group")
    @PostMapping("/joinToGroup")
    public UserGroupView joinToGroup(@Valid @RequestParam(value = "code")@EnglishAndNumber String code) {
        return userGroupService.joinToGroup(code);
    }
}
