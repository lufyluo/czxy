package com.czxy.manage.model.vo.teacher;

import com.czxy.manage.model.vo.user.UserInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TeacherWechatInfo {
    @ApiModelProperty("老师ID")
    private Integer id;
    @ApiModelProperty("评价")
    private String comment;
    @ApiModelProperty("0-党校系统，1-领导干部系统，2-高校系统")
    private Integer system;
    @ApiModelProperty("职称：高级、一级、二级、三级")
    private String skill;
    @ApiModelProperty("星级：一级、二级、三级")
    private String star;
    @ApiModelProperty("区域")
    private String area;
    private String name;
    private String phone;
    private Integer gender;
    private Integer age;
    @ApiModelProperty("老师单位")
    private String orgName;
    @ApiModelProperty("民族")
    private String nation;
    @ApiModelProperty("籍贯")
    private String nativePlace;
    @ApiModelProperty("学历")
    private String education;
    @ApiModelProperty("政治面貌")
    private String politics;
    @ApiModelProperty("岗位")
    private String position;
}
