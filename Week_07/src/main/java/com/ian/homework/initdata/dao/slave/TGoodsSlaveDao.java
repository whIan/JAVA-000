package com.ian.homework.initdata.dao.slave;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ian.homework.initdata.model.TGoods;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TGoodsSlaveDao extends BaseMapper<TGoods> {
    int deleteByPrimaryKey(Integer id);

    int insert(TGoods record);

    int insertSelective(TGoods record);

    TGoods selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TGoods record);

    int updateByPrimaryKey(TGoods record);

    int batchInsert(@Param("list") List<TGoods> records);

    int truncateTable(String tableName);
}