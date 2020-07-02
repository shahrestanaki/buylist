package com.service.search;

import lombok.Data;

@Data
public class GoodsSearch extends SearchCriteriaList {
    private long groupId;

    public GoodsSearch(int page, int size, String sort, long groupId) {
        super(page, size, sort);
        this.groupId = groupId;
    }
}
