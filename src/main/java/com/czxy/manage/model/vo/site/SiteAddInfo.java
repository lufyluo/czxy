package com.czxy.manage.model.vo.site;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.czxy.manage.model.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class SiteAddInfo extends BaseEntity {
    private Integer id;
    @Excel(name = "点位名称")
    private String name;
    @ApiModelProperty("点位类型")
    private List<TypeInfo> types;
    @ApiModelProperty("联系人姓名")
    @Excel(name = "联系人姓名")
    private String contactorName;
    @ApiModelProperty("联系人电话")
    @Excel(name = "联系人电话")
    private String contactorPhone;
    @ApiModelProperty("点位主题")
    private TypeInfo topic;
    @ApiModelProperty("参观时间")
    @Excel(name = "参观时间")
    private String driveTime;
    @ApiModelProperty("点位详细地址")
    private String addr;
    @ApiModelProperty("点位描述")
    @Excel(name = "点位描述")
    private String description;
    private String pics;
    private String attachFiles;
    private Integer provinceId;
    private Integer cityId;
    private Integer countyId;
    @Excel(name = "费用")
    private Integer fee;
//    @Excel(name = "点位类型")
    private String typeName;
}
