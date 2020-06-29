package com.web;

import com.service.search.UserGroupService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usergroup")
@Api(value = "usergroup")
public class UserGroupController {

    @Autowired
    private UserGroupService userGroupService;

}
