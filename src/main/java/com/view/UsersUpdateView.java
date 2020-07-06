package com.view;


import com.model.BaseEntity;
import com.tools.INumber;
import com.tools.OnlyLetter;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;


@Data
public class UsersUpdateView extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @Pattern(regexp = OnlyLetter.PATTERN, message = "برای نام مستعار از حروف و عدد استفاده نمایید")
    @Length(min = 5, max = 20, message = "لطفا برای کد بین {min} و {max} حرف وارد نمایید")
    private String nickName;

    @Length(min = 1, max = 5, message = "لطفا برای آواتار بین {min} و {max} حرف وارد نمایید")
    @Pattern(regexp = INumber.PATTERN, message = "نوع داده وارد شده برای آواتار باید عددی باشد")
    private String avatar;

}
