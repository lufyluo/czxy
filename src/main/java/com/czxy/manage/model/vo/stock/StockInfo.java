package com.czxy.manage.model.vo.stock;

import lombok.Data;

@Data
public class StockInfo {
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
