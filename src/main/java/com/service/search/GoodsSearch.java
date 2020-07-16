package com.service.search;

import com.tools.PersianDate;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class GoodsSearch extends SearchCriteriaList {
    @NotNull
    private long groupId;
    @NotNull
    @PersianDate
    private String dateFa;

    public GoodsSearch(int page, int size, String sort, long groupId) {
        super(page, size, sort);
        this.groupId = groupId;
    }
}
