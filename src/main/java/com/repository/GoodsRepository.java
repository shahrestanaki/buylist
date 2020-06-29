package com.repository;

import com.model.Goods;
import com.view.GoodsView;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodsRepository extends GeneralRepository<Goods, GoodsView, Long> {

}
