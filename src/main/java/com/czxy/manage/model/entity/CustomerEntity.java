package com.czxy.manage.model.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CustomerEntity {
    private Integer id;
    private String name;
    @ApiModelProperty("单位名称")
    private String orgName;
    @ApiModelProperty("客户职位")
    private String position;
    private String phone;
    @ApiModelProperty("班级名称")
    private String className;
    @ApiModelProperty("客户星级")
    private Integer orgStar;
    private Integer orgId;
    private Integer studentId;
    private Integer userId;
}
