package com.repository;

import com.model.SingUpTemp;
import org.springframework.stereotype.Repository;

@Repository
public interface SingUpTempRepository extends GeneralRepository<SingUpTemp, Long> {

    SingUpTemp getByMobileAndSingDateStr(String mobile, String singDateStr);

    SingUpTemp getByCodeAndSingDateStr(String code, String singDateStr);
}
