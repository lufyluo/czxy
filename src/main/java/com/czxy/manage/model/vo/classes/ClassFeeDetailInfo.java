package com.czxy.manage.model.vo.classes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class ClassFeeDetailInfo {
    @ApiModelProperty("类别")
    private String type;
    @ApiModelProperty("名称")
    private String name;
    private String detail;
    @ApiModelProperty("支出")
    private Integer pay;
    @ApiModelProperty("现场点位")
    @JsonIgnore
    private List<ClassFeeDetailInfo> sites;
    @ApiModelProperty("教师课酬")
    @JsonIgnore
    private List<ClassFeeDetailInfo> teacherSalary;
}
