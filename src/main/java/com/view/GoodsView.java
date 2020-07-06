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
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
public class GoodsView extends BaseEntityView {
    @JMap
    private Long id;
    @JMap
    private Long groupId;
    @JMap
    private Date dateList;
    @JMap
    @Length(min = 2, max = 20, message = "برای نام کالا بین {min} و {max} حرف وارد نمایید")
    @Pattern(regexp= PersianOnly.PATTERN,message = "برای نام کالا تنها از حروف فارسی استفاده نمایید")
    private String name;
    @JMap
    @Max(value = 1000 , message = "حداکثر تعداد کالا {value} باید باشد")
    //@Min(value = 1 , message = "حداقل تعداد کالا {value} باید باشد")
    private Float counts;
    @JMap
    private UnitEnum unit;
    // @JMap
    private String unitName;
    @JMap

    //@Min(value = 1 , message = "حداقل قیمت کالا {value} باید باشد")
    private Integer minFee;
    @JMap

    @Max(value = 100000000 , message = "حداکثر قیمت کالا {value} باید باشد")
    private Integer maxFee;
    @JMap
    private FeeEnum unitFee;
    // @JMap
    private String unitFeeName;
    @JMap
    private Boolean buy;
    @JMap
    private Long buyerId;
    private String buyerName;
    private String buyerAvatar;

    @JMap
    @Length(min = 5, max = 255, message = "برای توضیحات کالا بین {min} و {max} حرف وارد نمایید")
    @Pattern(regexp= PersianOnly.PATTERN,message = "برای توضیحات کالا تنها از حروف فارسی استفاده نمایید")
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
