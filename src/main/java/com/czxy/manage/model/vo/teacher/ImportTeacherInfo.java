package com.czxy.manage.model.vo.teacher;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ImportTeacherInfo {
    @Excel(name = "姓名")
    @ApiModelProperty("姓名")
    private String name;
    @Excel(name = "联系电话")
    @ApiModelProperty("联系电话")
    private String phone;
    @ApiModelProperty("0-男,1-女,2-未知")
    @Excel(name = "性别",replace = { "男_0", "女_1" })
    private Integer gender;
    @ApiModelProperty("职务")
    @Excel(name = "职务")
    private String position;
    @ApiModelProperty("简介")
    @Excel(name = "简介")
    private String comment;
    @ApiModelProperty("身份证号码")
    @Excel(name = "身份证号码")
    private String idCard;
    @ApiModelProperty(hidden = true)
    private Integer userId;
    @ApiModelProperty("区域")
    @Excel(name = "区域")
    private String area;
    @ApiModelProperty("0-党校系统，1-领导干部系统，2-高校系统")
    @Excel(name = "所属系统",replace = {"党校系统_0","领导干部系统_1","高校系统_2"})
    private Integer system;
    @ApiModelProperty("职称：高级、一级、二级、三级")
    @Excel(name = "职称")
    private String skill;
    @ApiModelProperty("星级：一级、二级、三级")
    @Excel(name = "星级")
    private String star;
    @ApiModelProperty("工作单位")
    @Excel(name = "工作单位")
    private String orgName;
    @ApiModelProperty(hidden = true)
    private Integer orgId;
    @ApiModelProperty("课酬，单位 元/天")
    @Excel(name = "课酬")
    private Integer pay;
    @ApiModelProperty("学历")
    @Excel(name = "学历")
    private String education;
    @ApiModelProperty("民族")
    @Excel(name = "民族")
    private String nation;
}
