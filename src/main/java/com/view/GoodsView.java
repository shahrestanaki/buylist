package com.view;

import com.enump.FeeEnum;
import com.enump.UnitEnum;
import com.googlecode.jmapper.annotations.JMap;
import com.model.BaseEntityView;
import com.tools.INumber;
import com.tools.PersianOnly;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
public class GoodsView extends BaseEntityView {
    @JMap
    private Long id;

    @JMap
    @NotNull
    private Long groupId;

    @JMap
    @NotNull
    private String dateList;

    @JMap
    @Length(min = 2, max = 20, message = "نام کالا باید حداقل {min} و حداکثر {max} حرف داشته باشه")
    @Pattern(regexp= PersianOnly.PATTERN,message = "برای نام کالا فقط از حروف فارسی استفاده کن")
    private String name;

    @JMap
    @Max(value = 1000 , message = "حداکثر تعداد کالا رو می تونی {value} وارد کنی")
    @NotNull
    private Float counts;

    @JMap
    @NotNull
    private UnitEnum unit;
    private String unitName;

    @JMap
    private Integer minFee;

    @JMap
    @Max(value = 100000000 , message = "قیمت کالا از {value} نمیتونه بیشتر باشه")
    private Integer maxFee;

    @JMap
    private FeeEnum unitFee;
    private String unitFeeName;

    private Boolean buy;
    private Long buyerId;
    private String buyerName;
    private String buyerAvatar;

    @JMap
    @Length(min = 5, max = 255, message = "برای توضیحات کالا بین {min} و {max} حرف وارد کن")
    @Pattern(regexp= PersianOnly.PATTERN,message = "برای توضیحات کالا تنها از حروف فارسی استفاده کن")
    private String comment;

    public void setUnit(UnitEnum unit) {
        this.unit = unit;
        this.unitName = unit.getName();
    }

    public void setUnitFee(FeeEnum unitFee) {
        this.unitFee = unitFee;
        this.unitFeeName = unitFee.getName();
    }
}
