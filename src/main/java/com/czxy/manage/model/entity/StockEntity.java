package com.czxy.manage.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class StockEntity {
    private Integer id;
    private String type;
    private String goodsName;
    private String unit;
    private String spec;
    private Integer spend;
    private Integer op;
    private Integer originId;
    private Integer classId;
    private Integer userId;
    private Integer total;
    private String className;
    private String classMasterName;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedTime;
}
