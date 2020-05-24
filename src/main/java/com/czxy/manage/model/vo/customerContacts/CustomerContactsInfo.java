package com.czxy.manage.model.vo.customerContacts;

import com.czxy.manage.model.vo.files.FileInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class CustomerContactsInfo {
    private Integer id;
    @ApiModelProperty("需求培训方")
    private String name;
    @ApiModelProperty("主办方对接人姓名")
    private String contactorName;
    @ApiModelProperty("单位名称，客户名称")
    private String orgName;
    private Integer orgId;
    private Integer provinceId;
    private Integer provinceName;
    private Integer cityId;
    private Integer cityName;
    private Integer countyId;
    private Integer countyName;
    @ApiModelProperty("主办方对接人职务职位")
    private String contactorPosition;
    @ApiModelProperty("主办方对接人电话")
    private String contactorPhone;
    @ApiModelProperty(value = "培训对象",hidden = true)
    @JsonIgnore
    private String compositionName;
    @ApiModelProperty(value = "培训对象",hidden = true)
    @JsonIgnore
    private String compositionId;
    @ApiModelProperty("培训时间描述")
    private String trainTime;
    @ApiModelProperty("培训人数")
    private Integer num;
    @ApiModelProperty("学院方对接人")
    private String selfContactorName;
    @ApiModelProperty("备注")
    private String description;
    @ApiModelProperty("状态")
    private String state;
    @ApiModelProperty("课表id")
    private Integer arrangeId;
    @ApiModelProperty("附件")
    private List<FileInfo> files;
}
