package com.web;

import com.service.GoodsService;
import com.service.search.GoodsSearch;
import com.service.search.SearchCriteriaList;
import com.view.BuyGoodsDto;
import com.view.GoodsView;
import com.view.GroupView;
import com.view.SimplePageResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(value = "list of my group")
    @PostMapping("/list")
    public SimplePageResponse<GoodsView> list(@Valid @RequestBody GoodsSearch search) {
        return goodsService.list(search);
    }

    @ApiOperation(value = "delete goods by id")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
         goodsService.delete(id);
    }

    @PostMapping("/update")
    public GoodsView update(@Valid @RequestBody GoodsView view) {
        return goodsService.update(view);
    }

}
