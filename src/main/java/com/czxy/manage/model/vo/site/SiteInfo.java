package com.czxy.manage.model.vo.site;

import com.czxy.manage.model.entity.BaseEntity;
import lombok.Data;

import java.util.List;

@Data
public class SiteInfo extends BaseEntity {
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
    private String topicsName;
    private String typeName;
    private String typeDescription;
    private Integer typeCategory;
}