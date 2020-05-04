package com.czxy.manage.model.vo.teacher;

import com.czxy.manage.model.vo.user.UserInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TeacherDetailInfo extends UserInfo {
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
    @ApiModelProperty("课酬，单位 元/天")
    private Integer pay;
}
