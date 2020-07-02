package com.service.mapper;

import com.model.Goods;
import com.view.GoodsView;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

public class MapperGoods {
    public static MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

    public static MapperFacade mapper() {
        mapperFactory.classMap(Goods.class, GoodsView.class).byDefault()
                .field("buyer.nickName", "buyerName")
                .field("buyer.avatar", "buyerAvatar").register();
        return mapperFactory.getMapperFacade();
    }

    public static MapperFacade mapperToModel() {
        mapperFactory.classMap(GoodsView.class, Goods.class).byDefault()
                .register();
        return mapperFactory.getMapperFacade();
    }
}
