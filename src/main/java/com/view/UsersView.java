package com.view;


import com.model.BaseEntityView;
import lombok.Data;

import java.util.Date;


@Data
public class UsersView extends BaseEntityView {
    private static final long serialVersionUID = 1L;

    private String mobile;
    private String nickName;
    private String avatar;
    private Date createDate;
}
