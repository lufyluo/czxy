package com.czxy.manage.model.entity;

import lombok.Data;

@Data
public class SiteEntity extends BaseEntity {
    private int id;
    private String name;
    private String types;
    private String contactorName;
    private String contactorPhone;
    private int addressId;
    private String topics;
}
