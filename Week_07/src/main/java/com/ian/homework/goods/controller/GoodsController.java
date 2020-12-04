package com.ian.homework.goods.controller;

import com.ian.homework.goods.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GoodsController {
    @Autowired
    private IGoodsService goodsService;

    @RequestMapping(value = "/hello")
    public String hello(){
        return "index";
    }

}
