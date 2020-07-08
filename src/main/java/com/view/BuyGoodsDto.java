package com.view;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class BuyGoodsDto {
    @NotNull
    private long goodsId;
    @NotNull
    private boolean buy;
}
