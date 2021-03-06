package com.service;

import com.enump.SmsTypeEnum;
import com.exception.AppException;
import com.model.SingUpTemp;
import com.model.SmsHistory;
import com.model.Users;
import com.repository.UserRepository;
import com.tools.CorrectDate;
import com.tools.GeneralTools;
import com.tools.GetResourceBundle;
import com.tools.TokenRead;
import com.view.*;
import com.webservice.sms.SmsSend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class UserService {
    private final int MAX_SINGUP_SMS = Integer.valueOf(GetResourceBundle.getConfig.getString("MAX_SMS_SEND_USER"));
    private final int MAX_SMS_FOGOT_USER = Integer.valueOf(GetResourceBundle.getConfig.getString("MAX_SMS_FOGOT_USER"));
    private final String PASSWORD_KEY = "MKt7Y6qw7ACk5Crk";
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private SingUpTempService singUpTempSrv;

    @Autowired
    private WebServerService webServerSrv;

    @Autowired
    private SmsHistoryService smsHistorySrv;

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
            singUpTempSrv.sendSmsSingUp(temp, singUp);
            return new UserGeneralResponse(HttpStatus.OK);
            //return new UserGeneralResponse(HttpStatus.OK, code);
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
        long smsCount = smsHistorySrv.smsCountByMobile(forgetPasswordDto.getMobile());
        if (smsCount >= MAX_SMS_FOGOT_USER) {
            throw new AppException("sms.max.limit." + GeneralTools.createRandom(1, 3));
        }
        Users user = getByMobile(forgetPasswordDto.getMobile());
        if (user == null) {
            return new UserGeneralResponse(HttpStatus.OK);
        }
        Date before = CorrectDate.changeDate(forgetPasswordDto.getSingUpDate(), -10, Calendar.DATE);
        Date after = CorrectDate.changeDate(forgetPasswordDto.getSingUpDate(), 10, Calendar.DATE);

        if (user.getCreateDate().after(before) && user.getCreateDate().before(after)) {
            String newPassword = webServerSrv.forgetPassword(user.getUserName());
            sendImmediatelySmsForgetPassword(user.getMobile(), newPassword);
        }

        return new UserGeneralResponse(HttpStatus.OK);
    }

    private void sendImmediatelySmsForgetPassword(String mobile, String password) {
        SmsSend.getInstance().sendSms(mobile, password,
                GetResourceBundle.getConfig.getString("sms.forgetPassword.TemplateId"));
        SmsHistory smsHistory = new SmsHistory(SmsTypeEnum.FORGET_PASSWORD, mobile, password);
        smsHistorySrv.log(smsHistory);
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
