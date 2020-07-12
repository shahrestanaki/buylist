package com.service;

import com.enump.SmsTypeEnum;
import com.model.SmsHistory;
import com.repository.SmsHistoryRepository;
import com.tools.CorrectDate;
import com.view.SmsHistoryView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SmsHistoryService extends GeneralService<Long, SmsHistory, SmsHistoryView> {

    @Autowired
    SmsHistoryRepository smsHistoryRepo;

    public void log(SmsHistory smsHistory) {
        smsHistoryRepo.save(smsHistory);
    }

    public long smsCountByMobile(String mobile) {
        return smsHistoryRepo.countByMobileAndDateStr(mobile, Long.valueOf(CorrectDate.miladiToShamsi(new Date(), "")));
    }

    public long smsCountByUser(long userId) {
        return smsHistoryRepo.countByUserIdAndDateStr(userId, Long.valueOf(CorrectDate.miladiToShamsi(new Date(), "")));
    }

    public long smsCountByUser(long userId, SmsTypeEnum type) {
        return smsHistoryRepo.countByUserIdAndSmsTypeAndDateStr(userId, type, Long.valueOf(CorrectDate.miladiToShamsi(new Date(), "")));
    }
}
