package com.view;

import com.enump.FeeEnum;
import com.enump.UnitEnum;
import com.googlecode.jmapper.annotations.JMap;
import com.model.BaseEntityView;
import lombok.Data;

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
    private String name;
    @JMap
    private float counts;
    @JMap
    private UnitEnum unit;
    // @JMap
    private String unitName;
    @JMap
    private Integer minFee;
    @JMap
    private Integer maxFee;
    @JMap
    private FeeEnum unitFee;
    // @JMap
    private String unitFeeName;
    @JMap
    private boolean buy;
    @JMap
    private Long buyerId;
    @JMap
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
