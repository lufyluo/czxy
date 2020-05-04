package com.czxy.manage.model.vo.site;

import com.czxy.manage.model.entity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty("点位主题名称")
    private String topicsName;
    @ApiModelProperty("点位类型名称")
    private String typeName;
    private String typeDescription;
    @ApiModelProperty("0代表类型，1代表主题")
    private Integer typeCategory;
}
