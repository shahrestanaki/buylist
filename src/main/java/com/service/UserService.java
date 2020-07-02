package com.service;

import com.exception.AppException;
import com.model.SingUpTemp;
import com.model.Users;
import com.repository.UserRepository;
import com.tools.GeneralTools;
import com.tools.TokenRead;
import com.view.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final int MAX_SINGUP_SMS = 3;
    private final String PASSWORD_KEY = "MKt7Y6qw7ACk5Crk";
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private SingUpTempService singUpTempSrv;


    @Autowired
    private WebServerService webServerSrv;

    public UserGeneralResponse singup(SingUpDto singUp) {
        if (!singUp.getPassword().equals(singUp.getRepeatPassword())) {
            throw new AppException("singup.repeat.password.error");
        }
        try {
            singUp.setPassword(GeneralTools.encrypt(singUp.getPassword(), PASSWORD_KEY));
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("core.general.error");
        }
        Users user = getByMobile(singUp.getMobile());
        if (user != null) {
            throw new AppException("singup.user.exist");
        }
        SingUpTemp temp = singUpTempSrv.getTodaySingUp(singUp.getMobile());
        if (temp == null || temp.getCounts() <= MAX_SINGUP_SMS) {
            String code = singUpTempSrv.sendSmsSingUp(temp, singUp);
            //TODO  return new UserGeneralResponse(HttpStatus.OK);
            return new UserGeneralResponse(HttpStatus.OK, code);
        } else {
            throw new AppException("singup.max.in.today");
        }
    }

    public UserGeneralResponse confirmSingup(confirmSingupDto dto) {
        SingUpTemp temp = singUpTempSrv.getByCodeAndMobile(dto.getCode(), dto.getMobile());
        if (temp == null) {
            throw new AppException("singup.novalid.code" + GeneralTools.createRandom(1, 3));
        } else {
            String password;
            String username = GeneralTools.createRandom("all", 10);
            try {
                password = GeneralTools.decrypt(temp.getHashPassword(), PASSWORD_KEY);
            } catch (Exception e) {
                e.printStackTrace();
                throw new AppException("general.error");
            }
            webServerSrv.singUpUser(username, password);

            Users user = new Users(username, temp.getMobile(), null, "1");
            userRepo.save(user);

            return new UserGeneralResponse(HttpStatus.OK);
        }
    }

    public ResponseEntity<UserLoginResponse> login(LoginDto loginDto) {
        Users user = getByMobile(loginDto.getMobile());
        if (user == null) {
            throw new AppException("login.novalid.mobile" + GeneralTools.createRandom(1, 3));
        }
        loginDto.setUsername(user.getUserName());
        return webServerSrv.userLogin(loginDto);
    }

    public UserGeneralResponse forgetPassword(ForgetPasswordDto forgetPasswordDto) {
        Users user = getByMobile(forgetPasswordDto.getMobile());
        if (user == null) {
            throw new AppException("forgetPassword.novalid.mobile");
        }
        String newPassword = webServerSrv.forgetPassword(user.getUserName());
        // TODO Send sms password
        return new UserGeneralResponse(HttpStatus.OK, newPassword);
    }

    public UserGeneralResponse changePassword(ChangePasswordDto changePasswordDto) {
        Users user = getByUserName();
        if (user == null) {
            throw new AppException("general.error.novalid.username");
        }
        webServerSrv.changePassword(changePasswordDto);
        return new UserGeneralResponse(HttpStatus.OK);
    }

    public UserGeneralResponse logout() {
        webServerSrv.logout();
        return new UserGeneralResponse(HttpStatus.OK);
    }

    public UsersView info() {
        return null;///////////////UserMapper.INSTANCE.map(getByUserName());
    }

    public UsersView update(UsersUpdateView view) {
        Users user = getByUserName();
        user.setNickName(view.getNickName());
        user.setAvatar(view.getAvatar());
        return updateView(user);
    }

    public UsersView changeMobile(ChangeMobileDto mobileDto) {
        Users user = getByUserName();
        user.setMobile(mobileDto.getMobile());
        return updateView(user);
    }

    //TODO
    public UserGeneralResponse deleteMe() {
        return new UserGeneralResponse(HttpStatus.BAD_REQUEST);
    }


    private Users getByMobile(String mobile) {
        return userRepo.getByMobile(mobile);
    }

    private Users getByUserName() {
        return userRepo.getByUserName(TokenRead.getUserName());
    }

    private Users getByUserName(String username) {
        return userRepo.getByUserName(username);
    }

    private Users update(Users users) {
        return userRepo.save(users);
    }

    private UsersView updateView(Users user) {
        return null;///////////////UserMapper.INSTANCE.map(userRepo.save(user));
    }

    public Users getCurrentUser() {
        return getByUserName(TokenRead.getUserName());
    }
}
