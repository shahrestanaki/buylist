package com.view;


import com.model.BaseEntity;
import lombok.Data;
import org.hibernate.validator.constraints.Length;


@Data
public class UsersUpdateView extends BaseEntity {
    private static final long serialVersionUID = 1L;


    @Length(min = 5, max = 20)
    private String nickName;

    @Length(min = 1, max = 20)
    private String avatar;

}
