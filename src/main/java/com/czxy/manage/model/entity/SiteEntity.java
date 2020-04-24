package com.czxy.manage.model.entity;

import lombok.Data;

@Data
public class SiteEntity extends BaseEntity {
    private Integer id;
    private String name;
    private String types;
    private String contactorName;
    private String contactorPhone;
    private Integer addressId;
    private String topics;
    private String driveTime;
}
