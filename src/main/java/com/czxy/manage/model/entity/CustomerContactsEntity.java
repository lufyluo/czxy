package com.czxy.manage.model.entity;

import lombok.Data;

@Data
public class CustomerContactsEntity {
    private Integer id;
    private String name;
    private String contactorName;
    private String orgName;
    private Integer orgId;
    private Integer provinceId;
    private Integer provinceName;
    private Integer cityId;
    private Integer cityName;
    private Integer countyId;
    private Integer countyName;
    private String contactorPosition;
    private String contactorPhone;
    private String compositionName;
    private String compositionId;
    private String trainTime;
    private Integer num;
    private String selfContactorName;
    private String description;
    private String state;
}
