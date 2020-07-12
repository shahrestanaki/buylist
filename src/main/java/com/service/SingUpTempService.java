package com.service;

import com.enump.SmsTypeEnum;
import com.model.SingUpTemp;
import com.model.SmsHistory;
import com.repository.SingUpTempRepository;
import com.tools.CorrectDate;
import com.tools.GeneralTools;
import com.tools.GetResourceBundle;
import com.view.SingUpDto;
import com.webservice.sms.SmsSend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SingUpTempService extends GeneralService {

    @Autowired
    private SingUpTempRepository singUpTempRepo;

    @Autowired
    private SmsHistoryService smsHistorySrv;

    public SingUpTemp getTodaySingUp(String mobile) {
        return singUpTempRepo.getByMobileAndSingDateStr(mobile, CorrectDate.convertDate(new Date(), "-"));
    }

    public void sendSmsSingUp(SingUpTemp temp, SingUpDto dto) {
        String random = GeneralTools.createRandom("number", 7);
        if (temp != null) {
            temp.setCounts(temp.getCounts() + 1);
            temp.setCode(random);
        } else {
            temp = new SingUpTemp();
            temp.setCounts(0);
            temp.setCode(random);
            temp.setMobile(dto.getMobile());
            temp.setSingDateStr(CorrectDate.convertDate(new Date(), "-"));
            temp.setHashPassword(dto.getPassword());
        }
        singUpTempRepo.save(temp);
        sendImmediatelySmsSingUp(temp);
    }

    private void sendImmediatelySmsSingUp(SingUpTemp temp) {
        SmsSend.getInstance().sendSms(temp.getMobile(), temp.getCode(),
                GetResourceBundle.getConfig.getString("sms.singup.TemplateId"));
        SmsHistory smsHistory = new SmsHistory(SmsTypeEnum.LOGIN,temp.getMobile(),temp.getCode());
        smsHistorySrv.log(smsHistory);
    }

    public SingUpTemp getByCodeAndMobile(String code, String mobile) {
        return singUpTempRepo.getByCodeAndMobileAndSingDateStr(code, mobile, CorrectDate.convertDate(new Date(), "-"));
    }


}
