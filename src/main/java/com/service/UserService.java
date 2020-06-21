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
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final int MAX_SINGUP_SMS = 3;
    private final String PASSWORD_KEY = "dw9FG25GH#97";
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

    public UserLoginResponse confirmSingup(String code) {
        SingUpTemp temp = singUpTempSrv.getByCodeAndSingDateStr(code);
        if (temp == null) {
            throw new AppException("singup.novalid.code" + GeneralTools.createRandom(1, 3));
        } else {
            String password;
            String username = GeneralTools.createRandom("password", 0) + code;
            try {
                password = GeneralTools.decrypt(temp.getHashPassword(), PASSWORD_KEY);
            } catch (Exception e) {
                e.printStackTrace();
                throw new AppException("general.error");
            }
            webServerSrv.singUpUser(username, password);

            Users user = new Users(username, temp.getMobile(), null, "1");
            userRepo.save(user);

            return new UserLoginResponse();
        }
    }

    public UserLoginResponse login(LoginDto loginDto) {
        Users user = getByMobile(loginDto.getMobile());
        if (user == null) {
            throw new AppException("login.novalid.mobile" + GeneralTools.createRandom(1, 3));
        }
        loginDto.setUsername(user.getUserName());
        return webServerSrv.login(loginDto);
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

    public UserGeneralResponse changePassword(ChangePasswordDto changePasswordDto, String user) {
        TokenRead.getUserName();

        return null;
    }

    public UserGeneralResponse logout() {
        return null;
    }

    public UsersView info() {
        return null;
    }

    public UsersUpdateView update(UsersUpdateView view) {
        return null;
    }

    public UserGeneralResponse changeMobile(ChangeMobileDto mobileDto) {
        return null;
    }

    public UserGeneralResponse deleteMe() {
        return null;
    }


    private Users getByMobile(String mobile) {
        return userRepo.getByMobile(mobile);
    }
}
