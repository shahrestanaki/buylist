package com.web;

import com.service.UserService;
import com.tools.OnlyLetter;
import com.view.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@Api(value = "user")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "sign-up new user")
    @PostMapping("/sign-up")
    public UserGeneralResponse singUp(@Valid @RequestBody SingUpDto singUp) {
        return userService.singup(singUp);
    }


    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(@Valid @RequestBody LoginDto loginDto) {
        return userService.login(loginDto);
    }

    @PostMapping("/forget-password")
    public UserGeneralResponse forgetPassword(@Valid @RequestBody ForgetPasswordDto forgetPasswordDto) {
        return userService.forgetPassword(forgetPasswordDto);
    }

    @PostMapping("/change-password")
    public UserGeneralResponse changePassword(@Valid @RequestBody ChangePasswordDto changePasswordDto) {
        return userService.changePassword(changePasswordDto);
    }

    @GetMapping("/logout")
    public UserGeneralResponse logout() {
        return userService.logout();
    }

    @GetMapping("/info")
    public UsersView info() {
        return userService.info();
    }

    @PostMapping("/update")
    public UsersView update(@Valid @RequestBody UsersUpdateView view) {
        return userService.update(view);
    }

    @PostMapping("/change-mobile")
    public UsersView changeMobile(@Valid @RequestBody ChangeMobileDto mobileDto) {
        return userService.changeMobile(mobileDto);
    }

    @PostMapping("/delete-me")
    public UserGeneralResponse deleteMe() {
        return userService.deleteMe();
    }

    @GetMapping("/confirm-singup")
    public UserGeneralResponse confirmSingup(@RequestParam(value = "code")  String code) {
        return userService.confirmSingup(code);
    }
}
