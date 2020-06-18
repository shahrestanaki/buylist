package com.view;


import com.model.BaseEntity;
import lombok.Data;

import java.util.Date;


@Data
public class UserInfoView extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String userName;
    private String password;
    private Boolean active;
    private Integer wrongPass;
    private Date lockDate;
    private Boolean lockStatus;
    private Date createDate;
    private Date changeDate;
    private String role;
    private String manager;


}
