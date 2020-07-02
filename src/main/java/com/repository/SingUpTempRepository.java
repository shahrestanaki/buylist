package com.repository;

import com.model.BaseEntityView;
import com.model.SingUpTemp;
import org.springframework.stereotype.Repository;

@Repository
public interface SingUpTempRepository extends GeneralRepository<SingUpTemp, BaseEntityView, Long> {

    SingUpTemp getByMobileAndSingDateStr(String mobile, String singDateStr);

    SingUpTemp getByCodeAndMobileAndSingDateStr(String code,String mobile, String singDateStr);
}
