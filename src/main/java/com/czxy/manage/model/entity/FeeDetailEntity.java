package com.czxy.manage.model.entity;

import lombok.Data;

import java.util.Date;

@Data
public class FeeDetailEntity extends BaseEntity {
    private Integer id;
    private Integer type;
    private String detail;
    private Integer pay;
    private Integer audit;
    private Integer totalPay;
    private Integer subtotal;
    private Integer classId;
}
