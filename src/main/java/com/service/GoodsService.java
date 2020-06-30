package com.service;

import com.exception.AppException;
import com.googlecode.jmapper.JMapper;
import com.model.Goods;
import com.repository.GoodsRepository;
import com.view.BuyGoodsDto;
import com.view.GoodsView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GoodsService extends GeneralService<Long, Goods, GoodsView> {
    JMapper<Goods, GoodsView> mapper = new JMapper<>(Goods.class, GoodsView.class);
    JMapper<GoodsView, Goods> mapperToView = new JMapper<>(GoodsView.class, Goods.class);
    @Autowired
    private GoodsRepository goodsRepo;

    @Autowired
    private GroupService groupService;

    public GoodsView create(GoodsView view) {
        view.setId(null);
        view.setBuy(false);
        checkAccessToGroup(view.getGroupId());
        return mapperToView.getDestination(goodsRepo.save(mapper.getDestination(view)));
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
            goods.get().setBuyerId(getUser().getId());
        } else {
            if (goods.get().getBuyerId().equals(getUser().getId())) {
                goods.get().setBuy(false);
                goods.get().setBuyerId(null);
            }
        }
        goodsRepo.save(goods.get());
    }
}
