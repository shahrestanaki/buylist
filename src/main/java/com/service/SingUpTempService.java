package com.service;

import com.model.SingUpTemp;
import com.repository.SingUpTempRepository;
import com.tools.CorrectDate;
import com.tools.GeneralTools;
import com.view.SingUpDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SingUpTempService extends GeneralService {

    @Autowired
    private SingUpTempRepository singUpTempRepo;

    public SingUpTemp getTodaySingUp(String mobile) {
        return singUpTempRepo.getByMobileAndSingDateStr(mobile, CorrectDate.convertDate(new Date(), "-"));
    }

    public String sendSmsSingUp(SingUpTemp temp, SingUpDto dto) {
        String random = GeneralTools.createRandom("number", 6);
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
        sendSms(temp);
        return random;
    }

    private void sendSms(SingUpTemp temp) {
        //TODO sendSms
    }

    public SingUpTemp getByCodeAndMobile(String code, String mobile) {
        return singUpTempRepo.getByCodeAndMobileAndSingDateStr(code, mobile, CorrectDate.convertDate(new Date(), "-"));
    }


}
