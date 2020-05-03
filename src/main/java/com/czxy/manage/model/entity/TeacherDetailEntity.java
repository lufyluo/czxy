package com.czxy.manage.model.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TeacherDetailEntity extends UserEntity {
    private Integer teacherId;
    @ApiModelProperty("得分")
    private Integer score;
    @ApiModelProperty("评价")
    private String comment;
    @ApiModelProperty("系统")
    private String system;
    @ApiModelProperty("职称：高级、一级、二级、三级")
    private String skill;
    @ApiModelProperty("星级：一级、二级、三级")
    private String star;
    @ApiModelProperty("工作单位")
    private String orgName;
    @ApiModelProperty("区域")
    private String area;
}
