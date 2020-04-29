package com.czxy.manage.model.entity;

import lombok.Data;

@Data
public class SiteEntity extends BaseEntity {
    private Integer id;
    private String name;
    private String types;
    private String contactorName;
    private String contactorPhone;
    private String topics;
    private String driveTime;
    private String addr;
    private String description;
    private String pics;
    private String attachFiles;
    private Integer provinceId;
    private Integer cityId;
    private Integer countyId;
    private Integer fee;
}
