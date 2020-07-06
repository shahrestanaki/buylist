package com.view;

import com.model.Group;
import lombok.Data;

@Data
public class GroupDetailsDto {

    private Group group;
    private SimplePageResponse<GoodsView> goodsList;
    boolean isAdmin;
    boolean isManager;

    public GroupDetailsDto(Group group, SimplePageResponse<GoodsView> goodsList, boolean isAdmin, boolean isManager) {
        this.group = group;
        this.goodsList = goodsList;
        this.isAdmin = isAdmin;
        this.isManager = isManager;
    }
}
