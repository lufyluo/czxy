package com.czxy.manage.model.vo.site;

import com.czxy.manage.model.entity.BaseEntity;
import com.czxy.manage.model.vo.files.FileInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class SiteInfo extends BaseEntity {
    private Integer id;
    private String name;
    private String types;
    @ApiModelProperty("点位类型名称")
    private String typeName;
    private String contactorName;
    private String contactorPhone;
    private Integer topicId;
    private String driveTime;
    private String addr;
    private String description;
    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private String pics;
    private List<FileInfo> pictureFileInfos;
    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private String attachFiles;
    private List<FileInfo> attachFileInfos;
    private Integer provinceId;
    private Integer cityId;
    private Integer countyId;
    private Integer fee;
    @ApiModelProperty("点位主题名称")
    private String topicName;
    @ApiModelProperty("点位详细地址（带省市县）")
    private String address;
    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private String path;
}
