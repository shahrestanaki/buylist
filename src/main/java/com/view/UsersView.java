package com.view;


import com.model.BaseEntity;
import lombok.Data;


@Data
public class UsersView extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private String mobile;
    private String nickName;
    private String avatar;
}
