package com.ian.homework.initdata.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * t_goods
 * @author 
 */
@Data
public class TGoods implements Serializable {
    @TableId(value="id",type= IdType.AUTO)
    private Integer id;

    private String goodsName;

    private String goodsType;

    private String goodsDetail;

    private Long createTime;

    private String createBy;

    private Long updateTime;

    private String updateBy;

    private static final long serialVersionUID = 1L;
}