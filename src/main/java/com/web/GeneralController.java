package com.web;

import com.enump.FeeEnum;
import com.enump.MessageEnum;
import com.enump.UnitEnum;
import com.view.Combo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/general")
@Api(value = "general")
public class GeneralController {


    @ApiOperation(value = "get unit goods list")
    @GetMapping("/goods-unit")
    public List<Combo> goodsUnitList() {
        return UnitEnum.getList();
    }

    @ApiOperation(value = "create goods list")
    @GetMapping("/goods-fee")
    public List<Combo> goodsFee() {
        return FeeEnum.getList();
    }

    @ApiOperation(value = "message type")
    @GetMapping("/message-type")
    public List<Combo> messageCombo() {
        return MessageEnum.getList();
    }

}
