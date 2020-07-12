package com.repository;

import com.enump.SmsTypeEnum;
import com.model.SmsHistory;
import com.view.SmsHistoryView;
import org.springframework.stereotype.Repository;

@Repository
public interface SmsHistoryRepository extends GeneralRepository<SmsHistory, SmsHistoryView, Long> {

    long countByMobileAndDateStr(String mobile, long DateStr);

    long countByUserIdAndDateStr(long userId, long DateStr);

    long countByUserIdAndSmsTypeAndDateStr(long userId, SmsTypeEnum type, long DateStr);
}
