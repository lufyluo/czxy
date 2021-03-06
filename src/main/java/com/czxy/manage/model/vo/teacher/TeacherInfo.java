package com.czxy.manage.model.vo.teacher;

import com.czxy.manage.model.vo.user.UserInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TeacherInfo extends UserInfo {
    @ApiModelProperty("得分")
    private Integer score;
    @ApiModelProperty("评价")
    private String comment;
    @ApiModelProperty(hidden = true)
    private Integer userId;
    @ApiModelProperty("0-党校系统，1-领导干部系统，2-高校系统")
    private Integer system;
    @ApiModelProperty("职称：高级、一级、二级、三级")
    private String skill;
    @ApiModelProperty("星级：一级、二级、三级")
    private String star;
    @ApiModelProperty("区域")
    private String area;
    @ApiModelProperty("中文推荐单位名字")
    private String orgName;
    @ApiModelProperty("课酬，单位 元/天")
    private Integer pay;
}
