package com.web;

import com.exception.ToManyRequestException;
import com.service.UserService;
import com.view.*;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Duration;

@RestController
@RequestMapping("/user")
@Api(value = "user")
public class UserController {
    private final Bucket bucketMinutes;
    private final Bucket bucketSecond;
    private final Bucket bucketDays;

    @Autowired
    private UserService userService;

    public UserController() {
        Bandwidth limit = Bandwidth.classic(3, Refill.greedy(1, Duration.ofMinutes(1)));
        this.bucketMinutes = Bucket4j.builder()
                .addLimit(limit)
                .build();

        Bandwidth limitSecond = Bandwidth.classic(1, Refill.greedy(1, Duration.ofSeconds(10)));
        this.bucketSecond = Bucket4j.builder()
                .addLimit(limitSecond)
                .build();

        Bandwidth limitDays = Bandwidth.classic(5, Refill.greedy(5, Duration.ofDays(1)));
        this.bucketDays = Bucket4j.builder()
                .addLimit(limitDays)
                .build();
    }

    @ApiOperation(value = "sign-up new user")
    @PostMapping("/sign-up")
    public UserGeneralResponse singUp(@Valid @RequestBody SingUpDto singUp) {
        if (bucketDays.tryConsume(1)) {
            return userService.singup(singUp);
        }
        throw new ToManyRequestException("Day");
    }


    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(@Valid @RequestBody LoginDto loginDto) {
        if (bucketMinutes.tryConsume(1)) {
            return userService.login(loginDto);
        }
        throw new ToManyRequestException("Minutes");
    }

    @PostMapping("/forget-password")
    public UserGeneralResponse forgetPassword(@Valid @RequestBody ForgetPasswordDto forgetPasswordDto) {
        if (bucketSecond.tryConsume(1)) {
            return userService.forgetPassword(forgetPasswordDto);
        }
        throw new ToManyRequestException("Second");
    }

    @PostMapping("/change-password")
    public UserGeneralResponse changePassword(@Valid @RequestBody ChangePasswordDto changePasswordDto) {
        return userService.changePassword(changePasswordDto);
    }

    @GetMapping("/logout")
    public UserGeneralResponse logout() {
        if (bucketMinutes.tryConsume(1)) {
            return userService.logout();
        }
        throw new ToManyRequestException("Minutes");
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

    @PostMapping("/confirm-singup")
    public UserGeneralResponse confirmSingup(@Valid @RequestBody confirmSingupDto dto) {
        if (bucketSecond.tryConsume(1)) {
            return userService.confirmSingup(dto);
        }
        throw new ToManyRequestException("Second");
    }

  /*  //@ApiIgnore
    @GetMapping("/sms")
    public boolean sms() {
        SmsSend.getInstance().sendSms("09127205789", GeneralTools.createRandom("number", 5),
                GetResourceBundle.getConfig.getString("sms.singup.TemplateId"));
        return true;
    }*/
}
