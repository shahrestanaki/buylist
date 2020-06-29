package com.service;

import com.googlecode.jmapper.JMapper;
import com.model.Users;
import com.repository.GeneralRepository;
import com.service.search.SearchCriteriaList;
import com.view.GroupView;
import com.view.SimplePageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional(rollbackFor = Exception.class)
public class GeneralService<Pk, M, V> {
    protected JMapper<M, V> mapper;
    protected JMapper<V, M> mapperToView;

    @Autowired
    UserService userSrv;

    public Users getUser() {
        return userSrv.getCurrentUser();
    }
}
