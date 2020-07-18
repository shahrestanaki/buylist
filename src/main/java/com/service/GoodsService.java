package com.service;

import com.exception.AppException;
import com.model.Goods;
import com.repository.GoodsRepository;
import com.service.mapper.MapperGoods;
import com.service.search.GoodsSearch;
import com.view.BuyGoodsDto;
import com.view.GoodsView;
import com.view.SimplePageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GoodsService extends GeneralService<Long, Goods, GoodsView> {

    @Autowired
    private GoodsRepository goodsRepo;

    @Autowired
    private GroupService groupService;

    public GoodsView create(GoodsView view) {
        view.setId(null);
        view.setBuy(false);
        if (!groupService.userIsEditorOfGroup(getUser().getId(), view.getGroupId())) {
            throw new AppException("general.error.notAccess");
        }
        Goods goods = goodsRepo.save(MapperGoods.mapperToModel().map(view, Goods.class));
        return MapperGoods.mapper().map(goods, GoodsView.class);
    }

    private void checkAccessToGroup(long groupId) {
        groupService.securityGetGroup(groupId);
    }

    public void buyGoods(BuyGoodsDto obj) {
        Optional<Goods> goods = goodsRepo.findById(obj.getGoodsId());
        if (!goods.isPresent()) {
            throw new AppException("security.check.conflict");
        }
        checkAccessToGroup(goods.get().getGroupId());
        if (obj.isBuy()) {
            goods.get().setBuy(true);
            goods.get().setBuyerId(getUserGroup(goods.get().getGroupId()).getId());
        } else {
            if (goods.get().getBuyerId().equals(getUserGroup(goods.get().getGroupId()).getId())) {
                goods.get().setBuy(false);
                goods.get().setBuyerId(null);
            }
        }
        goodsRepo.save(goods.get());
    }

    public SimplePageResponse<GoodsView> list(GoodsSearch search) {
        checkAccessToGroup(search.getGroupId());

        Goods goods = new Goods();
        goods.setGroupId(search.getGroupId());
        //goods.setDateList(CorrectDate.miladiToShamsi(search.getDate(),"/"));
        goods.setDateList(search.getDateFa());

        SimplePageResponse<GoodsView> result = new SimplePageResponse<>();
        List<GoodsView> listView = new ArrayList<>();
        goodsRepo.list(goods, search).getContent().forEach(item ->
                listView.add(MapperGoods.mapper().map(item, GoodsView.class))
        );
        result.setContent(listView);
        result.setCount(listView.size());
        return result;

    }

    public SimplePageResponse<Goods> listModel(GoodsSearch search) {
        checkAccessToGroup(search.getGroupId());
        Goods goods = new Goods();
        goods.setGroupId(search.getGroupId());
        return goodsRepo.list(goods, search);
    }

    public void delete(long id) {
        Optional<Goods> goods = goodsRepo.findById(id);
        if (!goods.isPresent()) {
            throw new AppException("general.error.notAccess");
        }
        if (!groupService.userIsEditorOfGroup(getUser().getId(), goods.get().getGroupId())) {
            throw new AppException("general.error.notAccess");
        }

        if (goods.get().getBuyerId() != null) {
            throw new AppException("goods.error.delete.buy");
        }
        goodsRepo.deleteById(id);
    }

    public GoodsView update(GoodsView view) {
        Goods goods = MapperGoods.mapperToModel().map(view, Goods.class);
        if (!groupService.userIsEditorOfGroup(getUser().getId(), goods.getGroupId())) {
            throw new AppException("general.error.notAccess");
        }
        goods = goodsRepo.save(goods);
        return MapperGoods.mapper().map(goods, GoodsView.class);
    }
}
