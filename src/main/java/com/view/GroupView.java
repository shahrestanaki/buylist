package com.view;

import com.googlecode.jmapper.annotations.JMap;
import com.model.BaseEntityView;
import com.tools.INumber;
import com.tools.PersianOnly;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Pattern;

@Data
public class GroupView extends BaseEntityView {
    private static final long serialVersionUID = 1L;
    @JMap
    private Long id;

    @JMap
    @Length(min = 5, max = 20, message = "برای نام گروه بین {min} و {max} حرف وارد کن")
    @Pattern(regexp= PersianOnly.PATTERN,message = "برای نام گروه تنها از حروف فارسی استفاده کن")
    private String name;

    @JMap
    private String code;

    @JMap
    @Length(min = 5, max = 255, message = "برای توضیحات گروه بین {min} و {max} حرف وارد کن")
    @Pattern(regexp= PersianOnly.PATTERN,message = "برای توضیحات گروه تنها از حروف فارسی استفاده کن")
    private String about;

    @JMap
    @Length(min = 1, max = 5, message = "لطفا برای عکس گروه بین {min} و {max} حرف وارد کن")
    @Pattern(regexp= INumber.PATTERN,message = "نوع داده وارد شده برای عکس گروه باید عددی باشه")
    private String avatar;

}
