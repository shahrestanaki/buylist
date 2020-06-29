package com.view;

import com.googlecode.jmapper.annotations.JMap;
import com.model.BaseEntityView;
import lombok.Data;

import javax.persistence.MappedSuperclass;

@Data
public class GroupView extends BaseEntityView {
    private static final long serialVersionUID = 1L;
    @JMap
    private Long id;
    @JMap
    private String name;
    @JMap
    private String code;
    @JMap
    private String about;
    @JMap
    private String avatar;

}
