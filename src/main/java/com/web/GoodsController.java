package com.web;

import com.service.GoodsService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/goods")
@Api(value = "goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

}
