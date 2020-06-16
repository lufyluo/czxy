package com.czxy.manage.model.vo.site;

import com.czxy.manage.model.entity.BaseEntity;
import com.czxy.manage.model.vo.files.FileInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class SiteDetailInfo extends BaseEntity {
    private Integer id;
    private String name;
    private String contactorName;
    private String contactorPhone;
    private String driveTime;
    private String addr;
    private String description;
    private Integer provinceId;
    private Integer cityId;
    private Integer countyId;
    private Integer fee;
    @ApiModelProperty("点位主题")
    private List<TopicInfo> topics;
    @ApiModelProperty("点位详细地址（带省市县）")
    private String address;
    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private String path;
    private List<TypeInfo> types;
    private List<FileInfo> pics;
    private List<FileInfo> attachFiles;
}
