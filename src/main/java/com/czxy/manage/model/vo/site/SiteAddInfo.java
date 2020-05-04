package com.czxy.manage.model.vo.site;

import com.czxy.manage.model.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class SiteAddInfo extends BaseEntity {
    private Integer id;
    private String name;
    @ApiModelProperty("点位类型")
    private List<TypeInfo> types;
    private String contactorName;
    private String contactorPhone;
    @ApiModelProperty("点位主题")
    private List<TypeInfo> topics;
    private String driveTime;
    private String addr;
    private String description;
    private String pics;
    private String attachFiles;
    private Integer provinceId;
    private Integer cityId;
    private Integer countyId;
    private Integer fee;
    private String typeName;
    private String typeDescription;
    private Integer typeCategory;
}
