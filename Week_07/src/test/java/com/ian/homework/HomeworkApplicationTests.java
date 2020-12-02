package com.ian.homework;

import com.ian.homework.initdata.dao.TGoodsDao;
import com.ian.homework.initdata.dao.slave.TGoodsSlaveDao;
import com.ian.homework.initdata.model.TGoods;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest
class HomeworkApplicationTests {

    //@Autowired
    private TGoodsDao goodsDao;

    @Autowired
    private TGoodsSlaveDao TGoodsSlaveDao;

    @Test
    void contextLoads() {

    }

    @Test
    public void testDataSource() {
        //System.out.println(goodsDao.selectList(null));
        System.out.println(TGoodsSlaveDao.selectList(null));
    }


    @Test
    public void testInsert() {
        // 清空表
        System.out.println("truncateTable");
        goodsDao.truncateTable("t_goods");

        //单个插入
        int MAX = 1000000;
        int i = MAX;
        long start = System.currentTimeMillis();
        System.out.println("inert one by one");
        System.out.println("start > " + start);
        while(i>0) {
            i--;
            TGoods goods = new TGoods();
            goods.setGoodsName("iphone " + i);
            goods.setGoodsType("tech");
            goods.setGoodsDetail("iphone");
            goods.setCreateBy("ian");
            goods.setUpdateBy("ian");
            goods.setCreateTime(System.currentTimeMillis());
            goods.setUpdateTime(System.currentTimeMillis());
            goodsDao.insert(goods);
        }
        long end = System.currentTimeMillis();
        System.out.println("end > " + end);
        System.out.println("end-start=" + (end-start));

        //清空表
        System.out.println("truncateTable");
        goodsDao.truncateTable("t_goods");

        //批量插入
        i = MAX;
        int batch = 10000;
        start = System.currentTimeMillis();
        System.out.println("inert batch by " + batch);
        System.out.println("start > " + start);
        List<TGoods> goodsList = new ArrayList<>();
        while(i>0) {
            i--;
            TGoods goods = new TGoods();
            goodsList.add(goods);
            goods.setGoodsName("iphone " + i);
            goods.setGoodsType("tech");
            goods.setGoodsDetail("iphone");
            goods.setCreateBy("ian");
            goods.setUpdateBy("ian");
            goods.setCreateTime(System.currentTimeMillis());
            goods.setUpdateTime(System.currentTimeMillis());
            if((i%batch) == 0){
                goodsDao.batchInsert(goodsList);
                goodsList.clear();
            }
        }
        end = System.currentTimeMillis();
        System.out.println("end > " + end);
        System.out.println("end-start=" + (end-start));

        int total= goodsDao.selectCount(null);
        Assert.assertEquals(MAX, total);
        System.out.println(total);
    }

}
