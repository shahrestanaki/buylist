package com.web;

import com.service.GoodsService;
import com.view.BuyGoodsDto;
import com.view.GoodsView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/goods")
@Api(value = "goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @ApiOperation(value = "create goods list")
    @PostMapping("/create")
    public GoodsView create(@Valid @RequestBody GoodsView view) {
        return goodsService.create(view);
    }

    @ApiOperation(value = "user buy goods or refunds")
    @PostMapping("/buy-goods")
    public void buyGoods(@Valid @RequestBody BuyGoodsDto obj) {
        goodsService.buyGoods(obj);
    }

}
