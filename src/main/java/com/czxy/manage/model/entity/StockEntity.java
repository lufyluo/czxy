package com.czxy.manage.model.entity;

import lombok.Data;

import java.util.Date;

@Data
public class StockEntity extends BaseEntity {
    private Integer id;
    private String type;
    private Integer goodsId;
    private Integer unit;
    private String spec;
    private Integer spend;
    private Integer op;
    private Integer originId;
    private Integer classId;
    private Integer userId;
}
