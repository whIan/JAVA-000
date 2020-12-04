package com.ian.homework.goods.service.impl;

import com.ian.homework.goods.dao.TGoodsDao;
import com.ian.homework.goods.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoodsService implements IGoodsService {
    @Autowired
    private TGoodsDao goodsDao;



}
