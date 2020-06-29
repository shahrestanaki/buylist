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
}
